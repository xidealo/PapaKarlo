package com.bunbeauty.shared.domain.interactor.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.SelectableUserAddress
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
//Todo (tests)
class GetSelectableUserAddressListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo
) {
    suspend operator fun invoke(): List<SelectableUserAddress> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return emptyList()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        val token = dataStoreRepo.getToken() ?: return emptyList()
        return userAddressRepo.getUserAddressListByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
            token = token,
        ).map { userAddress ->
            SelectableUserAddress(
                uuid = userAddress.uuid,
                street = userAddress.street,
                house = userAddress.house,
                flat = userAddress.flat,
                entrance = userAddress.entrance,
                floor = userAddress.floor,
                comment = userAddress.comment,
                userUuid = userAddress.userUuid,
                isSelected = userAddress.uuid == userAddressRepo
                    .getSelectedAddressByUserAndCityUuid(
                        userUuid = userUuid,
                        cityUuid = cityUuid,
                    )?.uuid,
            )
        }
    }
}