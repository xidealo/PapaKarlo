package com.example.data_api.repository

import com.bunbeauty.domain.model.User
import com.bunbeauty.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor() : UserRepo {

    override suspend fun refreshUser() {
        //TODO("Not yet implemented")
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