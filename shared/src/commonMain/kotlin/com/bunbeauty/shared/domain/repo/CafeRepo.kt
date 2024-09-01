package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun getCafeList(selectedCityUuid: String): List<Cafe>
    suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String
    )

    suspend fun getCafeByUuid(cafeUuid: String): Cafe?
    suspend fun getSelectedCafeByUserAndCityUuid(userUuid: String, cityUuid: String): Cafe?
}
