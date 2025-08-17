package com.bunbeauty.shared.data.dao.user

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.UserEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class UserDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IUserDao {

    private val userEntityQueries = foodDeliveryDatabase.userEntityQueries

    override suspend fun insertUser(userEntity: UserEntity) {
        userEntityQueries.insertUser(
            uuid = userEntity.uuid,
            phone = userEntity.phone,
            email = userEntity.email
        )
    }

    override suspend fun getUserByUuid(uuid: String): UserEntity? {
        return userEntityQueries.getUserByUuid(uuid).executeAsOneOrNull()
    }

    override fun observeUserByUuid(uuid: String): Flow<UserEntity?> {
        return userEntityQueries.getUserByUuid(uuid).asFlow().mapToOneOrNull()
    }

    override suspend fun updateUserEmailByUuid(uuid: String, email: String) {
        userEntityQueries.updateUserEmailByUuid(
            uuid = uuid,
            email = email
        )
    }

    override suspend fun deleteAll() {
        userEntityQueries.deleteAll()
    }
}
