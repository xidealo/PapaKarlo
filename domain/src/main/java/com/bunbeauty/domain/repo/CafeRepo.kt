package com.bunbeauty.domain.repo

import androidx.lifecycle.LiveData
import com.bunbeauty.domain.model.ui.cafe.Cafe
import com.bunbeauty.domain.model.ui.cafe.CafeEntity
import kotlinx.coroutines.flow.Flow

interface CafeRepo {
    val cafeEntityListFlow: Flow<List<Cafe>>
    suspend fun refreshCafeList()
    fun getCafeById(cafeId: String): LiveData<Cafe>
    suspend fun getCafeEntityByDistrict(districtId: String): CafeEntity
}