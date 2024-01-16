package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.street.Street

@Deprecated("Unused")
interface StreetRepo {
    suspend fun getStreetList(userUuid: String, cityUuid: String): List<Street>
}