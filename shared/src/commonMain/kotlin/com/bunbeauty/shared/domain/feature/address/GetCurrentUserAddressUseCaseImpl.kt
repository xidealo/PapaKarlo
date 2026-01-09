package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.core.domain.repo.UserAddressRepo

interface GetCurrentUserAddressUseCase {
    suspend operator fun invoke(): UserAddress?
}

class GetCurrentUserAddressUseCaseImpl(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
) : GetCurrentUserAddressUseCase {
    override suspend operator fun invoke(): UserAddress? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null

        return userAddressRepo.getSelectedAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        ) ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        )
    }
}
