package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.OrderDao
import com.bunbeauty.data_firebase.dao.UserAddressDao
import com.bunbeauty.data_firebase.dao.UserDao
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.Profile
import com.bunbeauty.domain.repo.UserRepo
import com.example.domain_firebase.mapper.IOrderMapper
import com.example.domain_firebase.mapper.IUserMapper
import com.example.domain_firebase.model.entity.address.UserAddressEntity
import com.example.domain_firebase.model.entity.order.OrderStatusEntity
import com.example.domain_firebase.model.entity.user.UserEntity
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val orderDao: OrderDao,
    private val userAddressDao: UserAddressDao,
    private val firebaseRepo: FirebaseRepo,
    private val authUtil: IAuthUtil,
    private val userMapper: IUserMapper,
    private val orderMapper: IOrderMapper
) : UserRepo {

    override suspend fun refreshUser(userUuid: String, userPhone: String) {
        if (authUtil.isAuthorize) {
            val userFirebase = firebaseRepo.getUser(userUuid).flowOn(IO).first()
            if (userFirebase == null) {
                val newUserFirebase =
                    com.example.domain_firebase.model.firebase.UserFirebase(phone = userPhone)
                firebaseRepo.postUser(userUuid, newUserFirebase)
                val newUserEntity =
                    UserEntity(uuid = userUuid, phone = userPhone, email = "")
                userDao.insert(newUserEntity)
            } else {
                val userWithAddresses = userMapper.toEntityModel(userFirebase, userUuid)
                userDao.insert(userWithAddresses.user)
                refreshUserAddresses(userWithAddresses.userAddressList)
                refreshOrders(userFirebase.orders.values.toList(), userUuid)
            }
        }
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getByUuid(userUuid)?.let { userEntity ->
            userMapper.toUIModel(userEntity)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeByUuid(userUuid)
            .flowOn(IO)
            .mapNotNull { userEntity ->
                userEntity?.let {
                    userMapper.toUIModel(userEntity)
                }
            }
            .flowOn(Default)
    }

    override suspend fun updateUserEmail(profile: Profile) {
        // TODO
    }

    suspend fun refreshOrders(userOrderFirebaseList: List<UserOrderFirebase>, userUuid: String) {
        firebaseRepo.removeOrderObservers()
        userOrderFirebaseList.forEach { userOrderFirebase ->
            firebaseRepo.getOrder(userOrderFirebase).collect { orderFirebase ->
                if (orderFirebase != null) {
                    val orderWithCartProducts =
                        orderMapper.toEntityModel(orderFirebase, userOrderFirebase, userUuid)

                    orderDao.insert(orderWithCartProducts)
                    observeActiveOrder(
                        orderWithCartProducts.order.orderStatus,
                        userOrderFirebase
                    )
                }
            }
        }
    }

    suspend fun observeActiveOrder(
        orderStatus: OrderStatus,
        userOrderFirebase: UserOrderFirebase
    ) {
        if (orderStatus != OrderStatus.CANCELED || orderStatus != OrderStatus.DELIVERED) {
            withContext(IO) {
                firebaseRepo.observeOrder(userOrderFirebase).onEach { updatedOrderFirebase ->
                    if (updatedOrderFirebase != null) {
                        val orderStatusEntity = OrderStatusEntity(
                            uuid = userOrderFirebase.orderUuid,
                            orderStatus = updatedOrderFirebase.orderEntity.orderStatus
                        )
                        orderDao.updateOrderStatus(orderStatusEntity)
                    }
                }.launchIn(this)
            }
        }
    }

    suspend fun refreshUserAddresses(userAddressList: List<UserAddressEntity>) {
        userAddressDao.deleteAll()
        userAddressDao.insertAll(userAddressList)
    }
}