package com.bunbeauty.shared.domain.use_case.address

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetSelectedCafeUseCase(
    private val cafeRepo: CafeRepo,
    private val dataStoreRepo: DataStoreRepo,
) {

    suspend operator fun invoke(): Cafe? {
        val userUuid = dataStoreRepo.getUserUuid() ?: return null
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: return null
        return cafeRepo.getSelectedCafeByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        ) ?: cafeRepo.getFirstCafeCityUuid(cityUuid = cityUuid)
    }
}