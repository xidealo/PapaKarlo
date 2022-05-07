package com.bunbeauty.domain.interactor.address

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import com.bunbeauty.shared.domain.repo.StreetRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class AddressInteractor(
    private val dataStoreRepo: DataStoreRepo,
    private val streetRepo: StreetRepo,
    private val userAddressRepo: UserAddressRepo,
    private val userInteractor: IUserInteractor
) : IAddressInteractor {

    override suspend fun createAddress(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String
    ): UserAddress? {
        if (!userInteractor.isUserAuthorize()) {
            return null
        }
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""
        val street =
            streetRepo.getStreetByNameAndCityUuid(streetName, cityUuid) ?: return null
        val createdUserAddress = CreatedUserAddress(
            house = house,
            flat = flat,
            entrance = entrance,
            floor = floor,
            comment = comment,
            streetUuid = street.uuid,
            isVisible = true
        )
        val token = dataStoreRepo.getToken() ?: return null
        val userAddress = userAddressRepo.saveUserAddress(token, createdUserAddress) ?: return null
        saveSelectedUserAddress(userAddress.uuid)

        return userAddress
    }

    override fun observeAddressList(): Flow<List<UserAddress>> {
        return dataStoreRepo.observeUserAndCityUuid().flatMapLatest { userCityUuid ->
            userAddressRepo.observeUserAddressListByUserUuidAndCityUuid(
                userUuid = userCityUuid.userUuid,
                cityUuid = userCityUuid.cityUuid
            )
        }
    }

    override suspend fun observeAddress(): Flow<UserAddress?> {
        return dataStoreRepo.observeUserAndCityUuid().flatMapLatest { userCityUuid ->
            userAddressRepo.observeSelectedUserAddressByUserAndCityUuid(
                userUuid = userCityUuid.userUuid,
                cityUuid = userCityUuid.cityUuid
            ).flatMapLatest { userAddress ->
                if (userAddress == null) {
                    userAddressRepo.observeFirstUserAddressByUserAndCityUuid(
                        userCityUuid.userUuid,
                        userCityUuid.cityUuid
                    )
                } else {
                    flow { emit(userAddress) }
                }
            }
        }
    }

    override suspend fun saveSelectedUserAddress(addressUuid: String) {
        val userCityUuid = dataStoreRepo.getUserAndCityUuid()
        userAddressRepo.saveSelectedUserAddress(
            addressUuid,
            userCityUuid.userUuid,
            userCityUuid.cityUuid
        )
    }
}