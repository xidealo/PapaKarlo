package com.bunbeauty.domain.repository.street

import com.bunbeauty.data.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun insert(street: Street):Long
    fun getStreets(): Flow<List<Street>>
}