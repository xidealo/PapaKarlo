package com.bunbeauty.shared.domain.use_case.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo

interface GetUserAddressListUseCase {
    suspend operator fun invoke(): List<UserAddress>
}

class GetUserAddressListUseCaseImpl(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo
) : GetUserAddressListUseCase {
    override suspend operator fun invoke(): List<UserAddress> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return emptyList()
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return emptyList()
        val token = dataStoreRepo.getToken() ?: return emptyList()
        return userAddressRepo.getUserAddressListByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
            token = token
        )
    }
}
