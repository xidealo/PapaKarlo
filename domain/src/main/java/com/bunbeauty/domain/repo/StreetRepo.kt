package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street

interface StreetRepo {
    suspend fun refreshStreets()
    suspend fun getStreets(): List<Street>
}