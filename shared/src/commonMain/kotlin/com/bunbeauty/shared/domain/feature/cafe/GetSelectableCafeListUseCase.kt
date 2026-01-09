package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.core.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.core.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.core.domain.exeptions.NoUserUuidException
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class GetSelectableCafeListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo,
    private val getCafeListUseCase: GetCafeListUseCase,
    private val isPickupEnabledFromCafeUseCaseImpl: IsPickupEnabledFromCafeUseCase,
) {
    suspend operator fun invoke(): List<SelectableCafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidException()

        val selectedCafe = getSelectedCafe(userUuid, cityUuid)

        return getCafeListUseCase()
            .map { cafe ->
                SelectableCafe(
                    cafe = cafe,
                    isSelected = cafe.uuid == selectedCafe?.uuid,
                    canBePickup = isPickupEnabledFromCafeUseCaseImpl(cafeUuid = cafe.uuid),
                )
            }.sortedByDescending { selectableCafe ->
                selectableCafe.canBePickup
            }
    }

    private suspend fun getSelectedCafe(
        userUuid: String,
        cityUuid: String,
    ): Cafe? {
        val cafeList = getCafeListUseCase().ifEmpty { throw EmptyCafeListException() }
        val selectedCafe =
            cafeRepo.getSelectedCafeByUserAndCityUuid(
                userUuid = userUuid,
                cityUuid = cityUuid,
            ) ?: cafeList.firstOrNull { cafe ->
                isPickupEnabledFromCafeUseCaseImpl(cafeUuid = cafe.uuid)
            } ?: cafeList.firstOrNull()
        return selectedCafe
    }
}
