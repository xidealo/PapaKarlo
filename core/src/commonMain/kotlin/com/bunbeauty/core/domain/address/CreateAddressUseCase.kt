package com.bunbeauty.core.domain.address

import com.bunbeauty.core.model.Suggestion
import com.bunbeauty.core.model.address.CreatedUserAddress
import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.domain.repo.UserRepo

class CreateAddressUseCase(
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo,
) {
    suspend operator fun invoke(
        street: Suggestion,
        house: String,
        flat: String,
        entrance: String,
        floor: String,
        comment: String,
    ): UserAddress? {
        val createdUserAddress =
            CreatedUserAddress(
                street = street,
                house = house,
                flat = flat,
                entrance = entrance,
                floor = floor,
                comment = comment,
                isVisible = true,
            )
        val savedAddress = userAddressRepo.saveUserAddress(
            createdUserAddress = createdUserAddress,
        )
        userRepo.saveUserCafeUuid(cafeUuid = savedAddress?.cafeUuid.orEmpty())

        return savedAddress
    }
}
