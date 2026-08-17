package com.bunbeauty.shared.data.dao.user

import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IUserDao {
    private val userEntityQueries = foodDeliveryDatabase.userEntityQueries

    override suspend fun insertUser(userEntity: UserEntity) {
        userEntityQueries.insertUser(
            uuid = userEntity.uuid,
            phone = userEntity.phone,
            email = userEntity.email,
        )
    }

    override suspend fun getUserByUuid(uuid: String): UserEntity? = userEntityQueries.getUserByUuid(uuid).awaitAsOneOrNull()

    override fun observeUserByUuid(uuid: String): Flow<UserEntity?> =
        userEntityQueries
            .getUserByUuid(uuid)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override suspend fun updateUserEmailByUuid(
        uuid: String,
        email: String,
    ) {
        userEntityQueries.updateUserEmailByUuid(
            uuid = uuid,
            email = email,
        )
    }

    override suspend fun deleteAll() {
        userEntityQueries.deleteAll()
    }
}
