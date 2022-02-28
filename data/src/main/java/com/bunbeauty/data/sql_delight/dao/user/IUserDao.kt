package com.bunbeauty.data.sql_delight.dao.user

import database.UserEntity
import kotlinx.coroutines.flow.Flow

interface IUserDao {

    fun insertUser(userEntity: UserEntity)

    fun observeUserByUuid(uuid: String): Flow<UserEntity?>

    fun updateUserEmailByUuid(uuid: String, email: String)
}