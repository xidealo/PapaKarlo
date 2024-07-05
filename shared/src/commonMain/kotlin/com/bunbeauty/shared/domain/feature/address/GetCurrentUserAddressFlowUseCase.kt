package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.repo.UserAddressRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GetCurrentUserAddressFlowUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<UserAddress?> {
        val userUuid = dataStoreRepo.getUserUuid() ?: return flowOf(null)
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return flowOf(null)

        return userAddressRepo.observeSelectedUserAddressByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        ).flatMapLatest { userAddress ->
            if (userAddress == null) {
                userAddressRepo.observeFirstUserAddressByUserAndCityUuid(
                    userUuid = userUuid,
                    cityUuid = cityUuid,
                )
            } else {
                flowOf(userAddress)
            }
        }
    }
}