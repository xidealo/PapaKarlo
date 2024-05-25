package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetSelectableCafeListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo,
    private val getCafeListUseCase: GetCafeListUseCase,
) {
    suspend operator fun invoke(): List<SelectableCafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidException()

        val cafeList = getCafeListUseCase()
        val selectedCafe = cafeRepo.getSelectedCafeByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid,
        ) ?: cafeList.firstOrNull()

        return getCafeListUseCase().map { cafe ->
            SelectableCafe(
                cafe = cafe,
                isSelected = cafe.uuid == selectedCafe?.uuid,
            )
        }
    }
}