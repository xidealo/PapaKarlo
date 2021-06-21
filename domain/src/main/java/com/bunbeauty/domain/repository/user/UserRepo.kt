package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun insertToBonusList(user: User)
    fun getUserAsFlow(userId: String): Flow<User?>
}