package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.local.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun insert(user: User)
    suspend fun insertToLocal(user: User)
    suspend fun insert(userFirebase: UserFirebase, userId: String)
    suspend fun update(user: User)
    suspend fun insertToBonusList(user: User)
    fun getUserWithBonuses(userId: String): Flow<User?>
    fun getUserAsFlowFromFirebase(userId: String): Flow<User?>
    fun getUserFirebaseAsFlow(userId: String): Flow<UserFirebase?>
}