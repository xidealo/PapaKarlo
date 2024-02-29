package com.bunbeauty.shared.data.dao.user

import com.bunbeauty.shared.db.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserDao {

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun getUserByUuid(uuid: String): UserEntity?

    fun observeUserByUuid(uuid: String): Flow<UserEntity?>

  suspend  fun updateUserEmailByUuid(uuid: String, email: String)
}