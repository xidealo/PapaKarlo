package com.bunbeauty.shared.domain.feature.cafe

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.EmptyCafeListException
import com.bunbeauty.shared.domain.exeptions.NoSelectedCityUuidException
import com.bunbeauty.shared.domain.exeptions.NoUserUuidException
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.SelectableCafe
import com.bunbeauty.shared.domain.repo.CafeRepo

//TODO (add test for canBePickup and sort by canBePickup)
class GetSelectableCafeListUseCase(
    private val dataStoreRepo: DataStoreRepo,
    private val cafeRepo: CafeRepo,
    private val getCafeListUseCase: GetCafeListUseCase,
    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase,
) {
    suspend operator fun invoke(): List<SelectableCafe> {
        val cityUuid = dataStoreRepo.getSelectedCityUuid() ?: throw NoSelectedCityUuidException()
        val userUuid = dataStoreRepo.getUserUuid() ?: throw NoUserUuidException()

        val selectedCafe = getSelectedCafe(userUuid, cityUuid)

        return getCafeListUseCase().map { cafe ->
            SelectableCafe(
                cafe = cafe,
                isSelected = cafe.uuid == selectedCafe?.uuid,
                canBePickup = isPickupEnabledFromCafeUseCase(cafeUuid = cafe.uuid)
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
        val selectedCafe = cafeRepo.getSelectedCafeByUserAndCityUuid(
            userUuid = userUuid,
            cityUuid = cityUuid
        ) ?: cafeList.firstOrNull { cafe ->
            isPickupEnabledFromCafeUseCase(cafeUuid = cafe.uuid)
        } ?: cafeList.firstOrNull()
        return selectedCafe
    }
}
