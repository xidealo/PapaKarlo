package com.bunbeauty.data.repository

import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.data.mapper.user.IUserEntityMapper
import com.bunbeauty.data.mapper.user.IUserFirebaseMapper
import com.bunbeauty.domain.model.data.User
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.entity.UserEntity
import com.bunbeauty.domain.repo.UserRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiRepo: ApiRepo,
    private val userFirebaseMapper: IUserFirebaseMapper,
    private val userEntityMapper: IUserEntityMapper
) : UserRepo {

    override fun getUserByUuid(userUuid: String?): Flow<User?> {
        return if (userUuid == null) {
            flowOf(null)
        } else {
            userDao.getByUuid(userUuid)
                .flowOn(IO)
                .map { userEntity ->
                    userEntity?.let {
                        userEntityMapper.from(userEntity)
                    }
                }
                .flowOn(Default)
        }
    }





    override suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
        apiRepo.insert(userFirebaseMapper.to(userEntity), userEntity.uuid)
    }

    override suspend fun insertToLocal(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    override suspend fun insert(userFirebase: UserFirebase, userId: String) {
        //insertToLocal(userFirebaseMapper.to(userFirebase).also { it.uuid = userId })
    }

    override suspend fun update(userEntity: UserEntity) {
        userDao.update(userEntity)
        //apiRepo.update(userFirebaseMapper.from(userEntity), userEntity.uuid)
    }

    override suspend fun insertToBonusList(userEntity: UserEntity) {
        //apiRepo.insertToBonusList(userFirebaseMapper.from(userEntity), userEntity.uuid)
    }

    override fun getUserWithBonuses(userId: String): Flow<UserEntity?> {
        return apiRepo.getUserBonusList(userId).flatMapLatest { bonusList ->
            userDao.getByUuid(userId)
//                .map {
//                it?.also { it.bonusList = bonusList.toMutableList() }
//            }
        }
    }

//    override fun getUser(userId: String): User? {
//        return userDao.getUser(userId)
//    }

    override fun getUserAsFlow(userId: String): Flow<UserEntity?> {
        return userDao.getByUuid(userId)
    }

    override fun getUserAsFlowFromFirebase(userId: String): Flow<UserEntity?> {
        return apiRepo.getUser(userId).map { userFirebase ->
            if (userFirebase != null && userFirebase.phone.isNotEmpty())
                userFirebaseMapper.from(userFirebase).also { it.uuid = userId }
            else
                null
        }
    }

    override fun getUserFirebaseAsFlow(userId: String): Flow<UserFirebase?> {
        return apiRepo.getUser(userId).map { userFirebase ->
            if (userFirebase != null && userFirebase.phone.isNotEmpty())
                userFirebase
            else
                null
        }
    }

}