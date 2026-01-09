package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.core.domain.exeptions.NoTokenException
import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo

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
        val createdUserAddress =
            CreatedUserAddress(
                street = street,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                isVisible = true,
                cityUuid = cityUuid,
            )
        val savedAddress =
            userAddressRepo.saveUserAddress(
                token = token,
                createdUserAddress = createdUserAddress,
            )

        dataStoreRepo.saveUserCafeUuid(userCafeUuid = savedAddress?.cafeUuid.orEmpty())

        return savedAddress
    }
}
