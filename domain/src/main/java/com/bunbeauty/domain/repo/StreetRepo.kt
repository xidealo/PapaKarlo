package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.Street
import kotlinx.coroutines.flow.Flow

interface StreetRepo {
    suspend fun getStreets(): List<Street>
}