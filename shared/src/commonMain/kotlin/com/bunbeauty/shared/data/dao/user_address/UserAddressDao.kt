package com.bunbeauty.shared.data.dao.user_address

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.db.UserAddressEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow

class UserAddressDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IUserAddressDao {

    private val userAddressEntityQueries = foodDeliveryDatabase.userAddressEntityQueries
    private val selectedUserAddressUuidEntityQueries =
        foodDeliveryDatabase.selectedUserAddressUuidEntityQueries

    override suspend fun insertUserAddress(userAddress: UserAddressEntity) {
        insertUserAddressList(listOf(userAddress))
    }

    override suspend fun insertUserAddressList(userAddressList: List<UserAddressEntity>) {
        userAddressEntityQueries.transaction {
            userAddressList.forEach { userAddress ->
                userAddressEntityQueries.insertUserAddress(
                    uuid = userAddress.uuid,
                    streetName = userAddress.streetName,
                    cityUuid = userAddress.cityUuid,
                    house = userAddress.house,
                    flat = userAddress.flat,
                    entrance = userAddress.entrance,
                    floor = userAddress.floor,
                    comment = userAddress.comment,
                    userUuid = userAddress.userUuid,
                )
            }
        }
    }

    override suspend fun insertSelectedUserAddressUuid(selectedUserAddressUuidEntity: SelectedUserAddressUuidEntity) {
        selectedUserAddressUuidEntityQueries.insertSelectedUserAddressUuid(
            userUuid = selectedUserAddressUuidEntity.userUuid,
            cityUuid = selectedUserAddressUuidEntity.cityUuid,
            userAddressUuid = selectedUserAddressUuidEntity.userAddressUuid,
        )
    }

    override suspend fun getUserAddressCountByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Long {
        return userAddressEntityQueries.getUserAddressCountByUserUuidAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).executeAsOne()
    }

    override fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?> {
        return userAddressEntityQueries.getSelectedUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).asFlow().mapToOneOrNull()
    }

    override suspend fun getSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): UserAddressEntity? {
        return userAddressEntityQueries.getSelectedUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).executeAsOneOrNull()
    }

    override suspend fun getUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): List<UserAddressEntity> {
        return userAddressEntityQueries.getUserAddressListByUserUuidAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).executeAsList()
    }

    override suspend fun geFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): UserAddressEntity? {
        return userAddressEntityQueries.getFirstUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).executeAsOneOrNull()
    }

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?> {
        return userAddressEntityQueries.getFirstUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).asFlow().mapToOneOrNull()
    }

    override fun observeUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddressEntity>> {
        return userAddressEntityQueries.getUserAddressListByUserUuidAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ).asFlow().mapToList()
    }

}