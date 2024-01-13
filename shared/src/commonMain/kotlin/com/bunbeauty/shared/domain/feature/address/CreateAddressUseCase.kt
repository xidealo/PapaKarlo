package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.model.Suggestion
import com.bunbeauty.shared.domain.model.address.CreatedUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo

class CreateAddressUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
) {
    suspend operator fun invoke(
        street: Suggestion,
        house: String,
        flat: String,
        entrance: String,
        floor: String,
        comment: String,
    ): UserAddress? {
        val token = dataStoreRepo.getToken() ?: throw NoTokenException()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val createdUserAddress = CreatedUserAddress(
            street = street,
            house = house,
            flat = flat,
            entrance = entrance,
            floor = floor,
            comment = comment,
            isVisible = true,
            cityUuid = cityUuid,
        )
        return userAddressRepo.saveUserAddress(
            token = token,
            createdUserAddress = createdUserAddress
        )
    }
}