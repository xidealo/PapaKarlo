package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.local.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun insert(street: Street)
    fun getStreets(): Flow<List<Street>>
}