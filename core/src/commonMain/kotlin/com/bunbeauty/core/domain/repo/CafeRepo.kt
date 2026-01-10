package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.cafe.Cafe


interface CafeRepo {
    suspend fun getCafeList(): List<Cafe>

    suspend fun saveSelectedCafeUuid(
        cafeUuid: String,
    )

    suspend fun getCafeByUuid(cafeUuid: String): Cafe?

    suspend fun getSelectedCafeByUserAndCityUuid(): Cafe?

    fun clearCache()
}
