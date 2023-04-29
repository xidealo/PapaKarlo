package com.bunbeauty.shared.domain.use_case.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo

class GetSelectedUserAddressUseCase(
    private val userAddressRepo: UserAddressRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): UserAddress? {
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