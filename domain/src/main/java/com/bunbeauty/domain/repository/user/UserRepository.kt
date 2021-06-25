package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.data.mapper.UserMapper
import com.bunbeauty.data.model.firebase.UserFirebase
import com.bunbeauty.data.model.user.User
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiRepository: IApiRepository,
    private val userMapper: UserMapper
) : UserRepo {

    override suspend fun insert(user: User) {
        userDao.insert(user)
        apiRepository.insert(userMapper.from(user), user.userId)
    }

    override suspend fun insertToLocal(user: User) {
        userDao.insert(user)
    }

    override suspend fun insert(userFirebase: UserFirebase, userId: String) {
        insertToLocal(userMapper.to(userFirebase).also { it.userId = userId })
    }

    override suspend fun update(user: User) {
        userDao.update(user)
        apiRepository.update(userMapper.from(user), user.userId)
    }

    override suspend fun insertToBonusList(user: User) {
        apiRepository.insertToBonusList(userMapper.from(user), user.userId)
    }

    override fun getUserWithBonuses(userId: String): Flow<User?> {
        return apiRepository.getUserBonusList(userId).flatMapLatest { bonusList ->
            userDao.getUserFlow(userId).map {
                it?.also { it.bonusList = bonusList.toMutableList() }
            }
        }
    }

    override fun getUserAsFlowFromFirebase(userId: String): Flow<User?> {
        return apiRepository.getUser(userId).map { userFirebase ->
            if (userFirebase != null && userFirebase.phone.isNotEmpty())
                userMapper.to(userFirebase).also { it.userId = userId }
            else
                null
        }
    }

    override fun getUserFirebaseAsFlow(userId: String): Flow<UserFirebase?> {
        return apiRepository.getUser(userId).map { userFirebase ->
            if (userFirebase != null && userFirebase.phone.isNotEmpty())
                userFirebase
            else
                null
        }
    }

}