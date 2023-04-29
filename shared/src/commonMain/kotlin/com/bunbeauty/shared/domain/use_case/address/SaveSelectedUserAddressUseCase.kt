package com.bunbeauty.shared.domain.use_case.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.repo.UserAddressRepo

class SaveSelectedUserAddressUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
) {
    suspend operator fun invoke(addressUuid: String) {
        val userCityUuid = dataStoreRepo.getUserAndCityUuid()
        userAddressRepo.saveSelectedUserAddress(
            addressUuid,
            userCityUuid.userUuid,
            userCityUuid.cityUuid
        )
    }
}