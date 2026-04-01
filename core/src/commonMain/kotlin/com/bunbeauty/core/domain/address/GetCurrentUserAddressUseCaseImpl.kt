package com.bunbeauty.core.domain.address

import com.bunbeauty.core.domain.repo.UserAddressRepo
import com.bunbeauty.core.model.address.UserAddress

interface GetCurrentUserAddressUseCase {
    suspend operator fun invoke(): UserAddress?
}

class GetCurrentUserAddressUseCaseImpl(
    private val userAddressRepo: UserAddressRepo,
) : GetCurrentUserAddressUseCase {
    override suspend operator fun invoke(): UserAddress? {
        val userAddress =
            userAddressRepo.getSelectedAddressByUserAndCityUuid()
                ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid()

        if (userAddress == null) {
            return null
        }

        return userAddressRepo.getSelectedAddressByUserAndCityUuid()
                    ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid()
    }
}
