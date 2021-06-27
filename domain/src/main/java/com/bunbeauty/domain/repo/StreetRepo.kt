package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun insert(street: Street):Long
    fun getStreets(): Flow<List<Street>>
}