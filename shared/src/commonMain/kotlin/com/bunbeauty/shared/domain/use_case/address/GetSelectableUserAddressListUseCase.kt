package com.bunbeauty.shared.domain.use_case.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressUseCase
import com.bunbeauty.core.model.address.SelectableUserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo

class GetSelectableUserAddressListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase,
) {
    suspend operator fun invoke(): List<SelectableUserAddress> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return emptyList()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        val token = dataStoreRepo.getToken() ?: return emptyList()
        val currentUserAddress = getCurrentUserAddressUseCase()

        return userAddressRepo
            .getUserAddressListByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
                token = token,
            ).map { userAddress ->
                SelectableUserAddress(
                    address = userAddress,
                    isSelected = userAddress.uuid == currentUserAddress?.uuid,
                )
            }
    }
}
