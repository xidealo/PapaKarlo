package com.bunbeauty.domain.cafe

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.cafe.CafeEntity
import kotlinx.coroutines.flow.Flow

interface CafeRepo {
    val cafeEntityListFlow: Flow<List<Cafe>>
    suspend fun refreshCafeList()
    fun getCafeById(cafeId: String): LiveData<Cafe>
    suspend fun getCafeEntityByDistrict(districtId: String): CafeEntity
}