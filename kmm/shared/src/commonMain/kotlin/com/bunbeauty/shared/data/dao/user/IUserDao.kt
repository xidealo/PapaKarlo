package com.bunbeauty.shared.data.dao.user

import com.bunbeauty.shared.db.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserDao {

    fun insertUser(userEntity: UserEntity)

    suspend fun getUserByUuid(uuid: String): UserEntity?

    fun observeUserByUuid(uuid: String): Flow<UserEntity?>

    fun updateUserEmailByUuid(uuid: String, email: String)
}