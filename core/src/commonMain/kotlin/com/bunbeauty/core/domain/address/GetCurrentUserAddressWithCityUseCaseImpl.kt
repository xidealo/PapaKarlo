package com.bunbeauty.core.domain.address

import com.bunbeauty.core.domain.city.GetSelectedCityUseCase
import com.bunbeauty.core.model.address.UserAddressWithCity
import com.bunbeauty.core.domain.repo.UserAddressRepo

interface GetCurrentUserAddressWithCityUseCase {
    suspend operator fun invoke(): UserAddressWithCity?
}

class GetCurrentUserAddressWithCityUseCaseImpl(
    private val userAddressRepo: UserAddressRepo,
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
) : GetCurrentUserAddressWithCityUseCase {
    override suspend operator fun invoke(): UserAddressWithCity? {
        val userAddress =
            userAddressRepo.getSelectedAddressByUserAndCityUuid()
                ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid()

        if (userAddress == null) {
            return null
        }

        return UserAddressWithCity(
            userAddress =
                userAddressRepo.getSelectedAddressByUserAndCityUuid()
                    ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid(),
            city = getSelectedCityUseCase()?.name,
        )
    }
}
