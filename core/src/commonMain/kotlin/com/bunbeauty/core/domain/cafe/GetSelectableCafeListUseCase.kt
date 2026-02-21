package com.bunbeauty.core.domain.cafe

import com.bunbeauty.core.domain.exeptions.EmptyCafeListException
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.model.cafe.SelectableCafe
import com.bunbeauty.core.domain.repo.CafeRepo

class GetSelectableCafeListUseCase(
    private val cafeRepo: CafeRepo,
    private val getCafeListUseCase: GetCafeListUseCase,
    private val isPickupEnabledFromCafeUseCaseImpl: IsPickupEnabledFromCafeUseCase,
) {
    suspend operator fun invoke(): List<SelectableCafe> {

        val selectedCafe = getSelectedCafe()

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

    private suspend fun getSelectedCafe(): Cafe? {
        val cafeList = getCafeListUseCase().ifEmpty { throw EmptyCafeListException() }
        val selectedCafe =
            cafeRepo.getSelectedCafeByUserAndCityUuid() ?: cafeList.firstOrNull { cafe ->
                isPickupEnabledFromCafeUseCaseImpl(cafeUuid = cafe.uuid)
            } ?: cafeList.firstOrNull()
        return selectedCafe
    }
}
