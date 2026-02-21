package com.bunbeauty.core.domain.address

import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.domain.repo.UserRepo

class SaveSelectedUserAddressUseCase(
    private val userAddressRepo: UserAddressRepo,
    private val userRepo: UserRepo
) {
    suspend operator fun invoke(addressUuid: String) {

        userAddressRepo.saveSelectedUserAddress(
            addressUuid = addressUuid,
        )

        val userAddress =
            userAddressRepo.getSelectedAddressByUserAndCityUuid()

        userRepo.saveUserCafeUuid(cafeUuid = userAddress?.cafeUuid.orEmpty())
    }
}
