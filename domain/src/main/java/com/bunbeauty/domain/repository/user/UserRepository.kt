package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.UserDao
import com.bunbeauty.data.mapper.UserMapper
import com.bunbeauty.data.model.user.User
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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

    override fun getUserAsFlow(userId: String): Flow<User?> {
        return userDao.getUserFlow(userId).flowOn(IO)
    }
}