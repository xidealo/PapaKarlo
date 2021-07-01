package com.bunbeauty.data.repository

import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.data.mapper.firebase.UserMapper
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.local.user.User
import com.bunbeauty.domain.repo.UserRepo
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiRepo: ApiRepo,
    private val userMapper: UserMapper
) : UserRepo {

    override suspend fun insert(user: User) {
        userDao.insert(user)
        apiRepo.insert(userMapper.from(user), user.userId)
    }

    override suspend fun insertToLocal(user: User) {
        userDao.insert(user)
    }

    override suspend fun insert(userFirebase: UserFirebase, userId: String) {
        insertToLocal(userMapper.to(userFirebase).also { it.userId = userId })
    }

    override suspend fun update(user: User) {
        userDao.update(user)
        apiRepo.update(userMapper.from(user), user.userId)
    }

    override suspend fun insertToBonusList(user: User) {
        apiRepo.insertToBonusList(userMapper.from(user), user.userId)
    }

    override fun getUserWithBonuses(userId: String): Flow<User?> {
        return apiRepo.getUserBonusList(userId).flatMapLatest { bonusList ->
            userDao.getUserFlow(userId).map {
                it?.also { it.bonusList = bonusList.toMutableList() }
            }
        }
    }

    override fun getUserAsFlowFromFirebase(userId: String): Flow<User?> {
        return apiRepo.getUser(userId).map { userFirebase ->
            if (userFirebase != null && userFirebase.phone.isNotEmpty())
                userMapper.to(userFirebase).also { it.userId = userId }
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