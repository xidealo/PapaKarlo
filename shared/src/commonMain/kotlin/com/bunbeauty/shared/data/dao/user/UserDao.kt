package com.bunbeauty.shared.data.dao.user

import app.cash.sqldelight.coroutines.asFlow
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.UserEntity
import com.bunbeauty.shared.extension.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class UserDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IUserDao {

    private val userEntityQueries = foodDeliveryDatabase.userEntityQueries

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
        return userEntityQueries.getUserByUuid(uuid)
            .asFlow()
            .mapToOneOrNull()
    }

    override fun updateUserEmailByUuid(uuid: String, email: String) {
        userEntityQueries.updateUserEmailByUuid(
            uuid = uuid,
            email = email
        )
    }

}