package com.bunbeauty.core.domain.address

import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class GetCurrentUserAddressFlowUseCase(
    private val userAddressRepo: UserAddressRepo,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<UserAddress?> {
        return userAddressRepo
            .observeSelectedUserAddressByUserAndCityUuid().flatMapLatest { userAddress ->
                if (userAddress == null) {
                    userAddressRepo.observeFirstUserAddressByUserAndCityUuid()
                } else {
                    flowOf(userAddress)
                }
            }
    }
}
