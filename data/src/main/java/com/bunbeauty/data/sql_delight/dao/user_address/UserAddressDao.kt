package com.bunbeauty.data.sql_delight.dao.user_address

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import database.SelectedUserAddressUuidEntity
import database.UserAddressEntity
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
                    streetUuid = userAddress.streetUuid,
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

    override fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?> {
        return userAddressEntityQueries.getSelectedUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .asFlow().mapToOneNotNull()
    }

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?> {
        return userAddressEntityQueries.getFirstUserAddressByUserAndCityUuid(userUuid, cityUuid)
            .asFlow().mapToOneNotNull()
    }

    override fun observeUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddressEntity>> {
        return userAddressEntityQueries.getUserAddressListByUserUuidAndCityUuid(userUuid, cityUuid)
            .asFlow().mapToList()
    }

    override fun observeUserAddressCountByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Long> {
        return userAddressEntityQueries.getUserAddressCountByUserUuidAndCityUuid(userUuid, cityUuid)
            .asFlow().mapToOne()
    }

}