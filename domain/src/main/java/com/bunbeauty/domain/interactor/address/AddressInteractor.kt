package com.bunbeauty.domain.interactor.address

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import javax.inject.Inject

class AddressInteractor @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    @Api private val streetRepo: StreetRepo,
    @Api private val userAddressRepo: UserAddressRepo,
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
        val cityUuid = dataStoreRepo.getSelectedCityUuid()
        val street =
            streetRepo.getStreetByNameAndCityUuid(streetName, cityUuid ?: "") ?: return null
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
        userAddressRepo.saveSelectedUserAddress(userAddress.userUuid)

        return userAddress
    }
}