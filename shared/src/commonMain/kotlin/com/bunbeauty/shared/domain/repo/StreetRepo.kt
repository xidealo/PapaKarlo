package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.street.Street

interface StreetRepo {
    suspend fun getStreetList(userUuid: String, cityUuid: String): List<Street>
    suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street?
}