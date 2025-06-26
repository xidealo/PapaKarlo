package com.bunbeauty.shared.domain.feature.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityUseCase
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.shared.domain.model.address.UserAddressWithCity
import com.bunbeauty.shared.domain.repo.UserAddressRepo

//TODO TESTS
interface GetCurrentUserAddressWithCityUseCase {
    suspend operator fun invoke(): UserAddressWithCity?
}

class GetCurrentUserAddressWithCityUseCaseImpl(
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressRepo: UserAddressRepo,
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
) : GetCurrentUserAddressWithCityUseCase {

    override suspend operator fun invoke(): UserAddressWithCity? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null

        return UserAddressWithCity(
            userAddress = userAddressRepo.getSelectedAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid
            ) ?: userAddressRepo.getFirstUserAddressByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid
            ),
            city = getSelectedCityUseCase()?.name
        )
    }
}
