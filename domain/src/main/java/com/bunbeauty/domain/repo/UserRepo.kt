package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.data.User
import com.bunbeauty.domain.model.firebase.UserFirebase
import com.bunbeauty.domain.model.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepo {

    fun getUser(userUuid: String): Flow<User?>


    // OLD
    suspend fun insert(userEntity: UserEntity)
    suspend fun insertToLocal(userEntity: UserEntity)
    suspend fun insert(userFirebase: UserFirebase, userId: String)
    suspend fun update(userEntity: UserEntity)
    suspend fun insertToBonusList(userEntity: UserEntity)
    fun getUserWithBonuses(userId: String): Flow<UserEntity?>
    //fun getUser(userId: String): User?
    fun getUserAsFlow(userId: String): Flow<UserEntity?>
    fun getUserAsFlowFromFirebase(userId: String): Flow<UserEntity?>
    fun getUserFirebaseAsFlow(userId: String): Flow<UserFirebase?>
}