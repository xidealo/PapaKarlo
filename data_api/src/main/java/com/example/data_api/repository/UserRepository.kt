package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.auth.AuthUtil
import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.UserRepo
import com.example.data_api.dao.UserDao
import com.example.domain_api.mapper.IUserMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val authUtil: AuthUtil,
    private val userMapper: IUserMapper,
    private val userDao: UserDao
) : UserRepo {

    override suspend fun refreshUser() {
        val userPhone = authUtil.userPhone
        val userUuid = authUtil.userUuid
        if (authUtil.isAuthorize && userPhone != null && userUuid != null) {
            when (val userResult = apiRepo.getUserByUuid(userUuid)) {

                is ApiResult.Success -> {
                    if(userResult.data == null) return

                    val userWithAddresses = userMapper.toEntityModel(userResult.data!!, userUuid)
                    userDao.insert(userWithAddresses.user)
                    //refreshUserAddresses(userWithAddresses.userAddressList)
                    //refreshOrders(userFirebase.orders.values.toList(), userUuid)
                }

                is ApiResult.Error -> {
                    /*     val newUserFirebase =
          com.example.domain_firebase.model.firebase.UserFirebase(phone = userPhone)
      apiRepo.postUser(userUuid, newUserFirebase)
      val newUserEntity =
          UserEntity(uuid = userUuid, phone = userPhone, email = null)
      userDao.insert(newUserEntity)*/
                }
            }
        }
    }

    override suspend fun getUserByUuid(userUuid: String): User? {
        //TODO("Not yet implemented")

        return Any() as User?
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        //TODO("Not yet implemented")

        return Any() as Flow<User?>
    }
}