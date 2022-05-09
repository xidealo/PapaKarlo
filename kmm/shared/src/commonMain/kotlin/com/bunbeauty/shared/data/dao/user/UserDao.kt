package com.bunbeauty.shared.data.dao.user

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import database.UserEntity
import kotlinx.coroutines.flow.Flow

class UserDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IUserDao {

    val userEntityQueries = foodDeliveryDatabase.userEntityQueries

    override fun insertUser(userEntity: UserEntity) {
        userEntityQueries.insertUser(
            uuid = userEntity.uuid,
            phone = userEntity.phone,
            email = userEntity.email,
        )
    }

    override suspend fun getUserByUuid(uuid: String): UserEntity? {
        return userEntityQueries.getUserByUuid(uuid).executeAsOneOrNull()
    }

    override fun observeUserByUuid(uuid: String): Flow<UserEntity?> {
        return userEntityQueries.getUserByUuid(uuid).asFlow().mapToOneOrNull()
    }

    override fun updateUserEmailByUuid(uuid: String, email: String) {
        userEntityQueries.updateUserEmailByUuid(
            uuid = uuid,
            email = email
        )
    }

}