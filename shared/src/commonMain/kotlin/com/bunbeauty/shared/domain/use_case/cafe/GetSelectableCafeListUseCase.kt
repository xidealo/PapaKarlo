package com.bunbeauty.shared.domain.use_case.cafe

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
                    uuid = cafe.uuid,
                    fromTime = cafe.fromTime,
                    toTime = cafe.toTime,
                    phone = cafe.phone,
                    address = cafe.address,
                    latitude = cafe.latitude,
                    longitude = cafe.longitude,
                    cityUuid = cafe.cityUuid,
                    isSelected = cafe.uuid == selectedCafe?.uuid,
                )
            }
    }
}