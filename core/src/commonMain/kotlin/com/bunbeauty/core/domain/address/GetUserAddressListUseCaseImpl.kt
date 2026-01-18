package com.bunbeauty.core.domain.address

import com.bunbeauty.core.model.address.UserAddress
import com.bunbeauty.core.domain.repo.UserAddressRepo

interface GetUserAddressListUseCase {
    suspend operator fun invoke(): List<UserAddress>
}

class GetUserAddressListUseCaseImpl(
    private val userAddressRepo: UserAddressRepo,
) : GetUserAddressListUseCase {
    override suspend operator fun invoke(): List<UserAddress> {
        return userAddressRepo.getUserAddressListByUserAndCityUuid()
    }
}
