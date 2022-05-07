package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.Street

interface StreetRepo {
    suspend fun getStreetList(selectedCityUuid: String): List<Street>
    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street?
}