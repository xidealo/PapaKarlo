package com.bunbeauty.shared.domain.interactor.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo

class GetUserAddressListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo
) {
    suspend operator fun invoke(): List<UserAddress> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return emptyList()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        val token = dataStoreRepo.getToken() ?: return emptyList()
        return userAddressRepo.getUserAddressListByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
            token = token,
        )
    }
}