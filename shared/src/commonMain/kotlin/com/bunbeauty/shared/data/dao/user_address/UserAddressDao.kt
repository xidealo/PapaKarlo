package com.bunbeauty.shared.data.dao.user_address

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.SelectedUserAddressUuidEntity
import com.bunbeauty.shared.db.UserAddressEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserAddressDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IUserAddressDao {
    private val userAddressEntityQueries = foodDeliveryDatabase.userAddressEntityQueries
    private val selectedUserAddressUuidEntityQueries =
        foodDeliveryDatabase.selectedUserAddressUuidEntityQueries

    override suspend fun insertUserAddress(userAddress: UserAddressEntity) {
        insertUserAddressList(userAddressList = listOf(userAddress))
    }

    override suspend fun insertUserAddressList(userAddressList: List<UserAddressEntity>) {
        userAddressEntityQueries.transaction {
            userAddressList.forEach { userAddressEntity ->
                userAddressEntity.run {
                    userAddressEntityQueries.insertUserAddress(
                        uuid = uuid,
                        streetName = streetName,
                        cityUuid = cityUuid,
                        house = house,
                        flat = flat,
                        entrance = entrance,
                        floor = floor,
                        comment = comment,
                        minOrderCost = minOrderCost,
                        normalDeliveryCost = normalDeliveryCost,
                        forLowDeliveryCost = forLowDeliveryCost,
                        lowDeliveryCost = lowDeliveryCost,
                        userUuid = userUuid,
                        cafeUuid = cafeUuid,
                    )
                }
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
        cityUuid: String,
    ): Long =
        userAddressEntityQueries
            .getUserAddressCountByUserUuidAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).awaitAsOne()

    override fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<UserAddressEntity?> =
        userAddressEntityQueries
            .getSelectedUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override suspend fun getSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): UserAddressEntity? =
        userAddressEntityQueries
            .getSelectedUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).awaitAsOneOrNull()

    override suspend fun getUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): List<UserAddressEntity> =
        userAddressEntityQueries
            .getUserAddressListByUserUuidAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).awaitAsList()

    override suspend fun geFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): UserAddressEntity? =
        userAddressEntityQueries
            .getFirstUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).awaitAsOneOrNull()

    override fun observeFirstUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<UserAddressEntity?> =
        userAddressEntityQueries
            .getFirstUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override fun observeUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Flow<List<UserAddressEntity>> =
        userAddressEntityQueries
            .getUserAddressListByUserUuidAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ).asFlow()
            .mapToList(Dispatchers.Default)

    override suspend fun deleteAll() {
        selectedUserAddressUuidEntityQueries.deleteAll()
        userAddressEntityQueries.deleteAll()
    }
}
