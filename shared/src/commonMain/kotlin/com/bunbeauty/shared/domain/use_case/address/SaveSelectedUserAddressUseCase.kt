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
            addressUuid = addressUuid,
            userUuid = userCityUuid.userUuid,
            cityUuid = userCityUuid.cityUuid,
        )

        val userAddress =
            userAddressRepo.getSelectedAddressByUserAndCityUuid(
                userUuid = userCityUuid.userUuid,
                cityUuid = userCityUuid.cityUuid,
            )

        dataStoreRepo.saveUserCafeUuid(userCafeUuid = userAddress?.cafeUuid.orEmpty())
    }
}
