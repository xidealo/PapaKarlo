package com.bunbeauty.core.domain.address

import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo

interface GetCurrentUserAddressUseCase {
    suspend operator fun invoke(): UserAddress?
}

class GetCurrentUserAddressUseCaseImpl(
    private val userAddressRepo: UserAddressRepo,
) : GetCurrentUserAddressUseCase {
    override suspend operator fun invoke(): UserAddress? {
        return userAddressRepo.getSelectedAddressByUserAndCityUuid()
            ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid()
    }
}
