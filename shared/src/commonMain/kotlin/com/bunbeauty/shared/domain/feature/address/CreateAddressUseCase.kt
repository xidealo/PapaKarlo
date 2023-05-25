package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoStreetByNameAndCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.StreetRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo

class CreateAddressUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val streetRepo: StreetRepo,
    private val userAddressRepo: UserAddressRepo,
) {
    suspend operator fun invoke(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String,
    ): UserAddress? {
        val token = dataStoreRepo.getToken() ?: throw NoTokenException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val street =
            streetRepo.getStreetByNameAndCityUuid(streetName, cityUuid)
                ?: throw NoStreetByNameAndCityUuidException()
        val createdUserAddress = CreatedUserAddress(
            house = house,
            flat = flat,
            entrance = entrance,
            floor = floor,
            comment = comment,
            streetUuid = street.uuid,
            isVisible = true
        )
        return userAddressRepo.saveUserAddress(token, createdUserAddress)
    }
}