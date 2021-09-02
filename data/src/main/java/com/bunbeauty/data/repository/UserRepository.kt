package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.mapper.IUserMapper
import com.bunbeauty.domain.model.entity.user.UserEntity
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.ui.User
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.UserRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiRepo: ApiRepo,
    private val userAddressRepo: UserAddressRepo,
    private val orderRepo: OrderRepo,
    private val authUtil: IAuthUtil,
    private val userMapper: IUserMapper
) : UserRepo {

    override suspend fun refreshUser() {
        val userPhone = authUtil.userPhone
        val userUuid = authUtil.userUuid
        if (authUtil.isAuthorize && userPhone != null && userUuid != null) {
            val userFirebase = apiRepo.getUser(userUuid).flowOn(IO).first()
            if (userFirebase == null) {
                val newUserFirebase = UserFirebase(phone = userPhone)
                apiRepo.postUser(userUuid, newUserFirebase)
                val newUserEntity =
                    UserEntity(uuid = userUuid, phone = userPhone, email = null)
                userDao.insert(newUserEntity)
            } else {
                val userWithAddresses = userMapper.toEntityModel(userFirebase, userUuid)
                userDao.insert(userWithAddresses.user)
                userAddressRepo.saveUserAddresses(userWithAddresses.userAddressList)
                orderRepo.refreshOrders(userFirebase.orders.values.toList(), userUuid)
            }
        }
    }

    override suspend fun getUserByUuid(userUuid: String): User? {
        return userDao.getByUuid(userUuid)?.let { userEntity ->
            userMapper.toUIModel(userEntity)
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeByUuid(userUuid)
            .flowOn(IO)
            .mapNotNull { userEntity ->
                userEntity?.let {
                    userMapper.toUIModel(userEntity)
                }
            }
            .flowOn(Default)
    }
}