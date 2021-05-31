package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.data.mapper.UserMapper
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

    override suspend fun update(user: User) {
        userDao.update(user)
        apiRepository.update(userMapper.from(user), user.userId)
    }

    override suspend fun updateBonusList(user: User) {
        userDao.update(user)
        apiRepository.updateBonusList(userMapper.from(user), user.userId)
    }

    override fun getUserAsFlow(userId: String): Flow<User?> {
        return apiRepository.getUser(userId).map { userFirebase ->
            if (userFirebase != null)
                userMapper.to(userFirebase).also { it.userId = userId}
            else
                null
        }
    }
}