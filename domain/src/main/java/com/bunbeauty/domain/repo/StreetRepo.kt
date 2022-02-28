package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun refreshStreetList(selectedCityUuid: String)
    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street?
    fun observeStreetListByCityUuid(cityUuid: String): Flow<List<Street>>
}