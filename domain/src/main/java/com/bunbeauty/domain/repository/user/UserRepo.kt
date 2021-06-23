package com.bunbeauty.domain.repository.user

import com.bunbeauty.data.model.firebase.UserFirebase
import com.bunbeauty.data.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun insert(user: User)
    suspend fun insertToLocal(user: User)
    suspend fun insert(userFirebase: UserFirebase, userId: String)
    suspend fun update(user: User)
    suspend fun insertToBonusList(user: User)
    fun getUserAsFlow(userId: String): Flow<User?>
    fun getUserFirebaseAsFlow(userId: String): Flow<UserFirebase?>
}