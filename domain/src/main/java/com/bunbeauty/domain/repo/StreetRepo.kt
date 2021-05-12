package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun refreshStreetList()
    suspend fun getStreets(): List<Street>
    fun observeStreetList(): Flow<List<Street>>
}