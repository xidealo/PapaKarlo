package com.bunbeauty.shared.domain.feature.city

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.address.UserAddressWithCity
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.repo.CityRepo

interface GetSelectedCityUseCase {
    suspend operator fun invoke(): City?
}

class GetSelectedCityUseCaseImpl(
    private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) : GetSelectedCityUseCase {

    override suspend operator fun invoke(): City? {
        return dataStoreRepo.getSelectedCityUuid()?.let { cityUuid ->
            cityRepo.getCityByUuid(cityUuid)
        }
    }
}
