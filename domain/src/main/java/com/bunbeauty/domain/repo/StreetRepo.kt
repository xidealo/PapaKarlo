package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street

interface StreetRepo {
    suspend fun refreshStreetList()
    suspend fun getStreets(): List<Street>
}