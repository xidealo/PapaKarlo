package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetSelectableCafeListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo,
) {
    suspend operator fun invoke(): List<SelectableCafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidException()

        val selectedCafe = cafeRepo.getSelectedCafeByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        ) ?: cafeRepo.getFirstCafeCityUuid(cityUuid = cityUuid)

        return cafeRepo
            .getCafeList(cityUuid)
            .ifEmpty { throw EmptyCafeListException() }
            .map { cafe ->
                SelectableCafe(
                    cafe = cafe,
                    isSelected = cafe.uuid == selectedCafe?.uuid,
                )
            }
    }
}