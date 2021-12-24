package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.OrderDao
import com.bunbeauty.data_firebase.dao.UserAddressDao
import com.bunbeauty.data_firebase.dao.UserDao
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.profile.Profile
import com.bunbeauty.domain.model.profile.User
import com.bunbeauty.domain.repo.UserRepo
import com.example.domain_firebase.mapper.IOrderMapper
import com.example.domain_firebase.mapper.IUserMapper
import com.example.domain_firebase.model.entity.address.UserAddressEntity
import com.example.domain_firebase.model.entity.order.OrderStatusEntity
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val orderDao: OrderDao,
    private val userAddressDao: UserAddressDao,
    private val firebaseRepo: FirebaseRepo,
    private val userMapper: IUserMapper,
    private val orderMapper: IOrderMapper
) : UserRepo {

    override suspend fun refreshUser(token: String) {
//        if (authUtil.isAuthorize) {
//            val userFirebase = firebaseRepo.getUser(token).flowOn(IO).first()
//            if (userFirebase == null) {
//                val newUserFirebase =
//                    com.example.domain_firebase.model.firebase.UserFirebase(phone = userPhone)
//                firebaseRepo.postUser(token, newUserFirebase)
//                val newUserEntity =
//                    UserEntity(uuid = token, phone = userPhone, email = "")
//                userDao.insert(newUserEntity)
//            } else {
//                val userWithAddresses = userMapper.toEntityModel(userFirebase, token)
//                userDao.insert(userWithAddresses.user)
//                refreshUserAddresses(userWithAddresses.userAddressList)
//                refreshOrders(userFirebase.orders.values.toList(), token)
//            }
//        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return flow {}
    }

    override suspend fun login(userUuid: String, userPhone: String): String? {
        return null
    }

    override suspend fun getUserByUuid(userUuid: String): Profile? {
        return userDao.getByUuid(userUuid)?.let { userEntity ->
            userMapper.toUIModel(userEntity)
        }
    }

    override fun observeProfileByUuid(userUuid: String): Flow<Profile?> {
        return userDao.observeByUuid(userUuid).map { userEntity ->
            if (userEntity == null) {
                null
            } else {
                userMapper.toUIModel(userEntity)
            }
        }
    }

    override suspend fun updateUserEmail(userUuid: String, email: String): User? {
        // TODO

        return null
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