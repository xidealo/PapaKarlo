package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun getStreetList(selectedCityUuid: String): List<Street>
    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street?
}