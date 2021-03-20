package com.bunbeauty.papakarlo.data.local.db.street

import com.bunbeauty.data.model.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun insert(street: Street):Long
    fun getStreets(): Flow<List<Street>>
}