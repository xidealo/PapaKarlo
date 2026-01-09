package com.bunbeauty.core.domain.address

import com.bunbeauty.core.model.address.SelectableUserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo

class GetSelectableUserAddressListUseCase(
    private val userAddressRepo: UserAddressRepo,
    private val getCurrentUserAddressUseCase: GetCurrentUserAddressUseCase,
) {
    suspend operator fun invoke(): List<SelectableUserAddress> {
        val currentUserAddress = getCurrentUserAddressUseCase()

        return userAddressRepo
            .getUserAddressListByUserAndCityUuid().map { userAddress ->
                SelectableUserAddress(
                    address = userAddress,
                    isSelected = userAddress.uuid == currentUserAddress?.uuid,
                )
            }
    }
}
