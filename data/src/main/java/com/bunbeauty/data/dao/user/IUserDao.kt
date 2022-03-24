package com.bunbeauty.data.dao.user

import database.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserDao {

    fun insertUser(userEntity: UserEntity)

    fun observeUserByUuid(uuid: String): Flow<UserEntity?>

    fun updateUserEmailByUuid(uuid: String, email: String)
}