package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.model.cafe.CafeEntity

interface CafeRepo {
    val cafeEntityListLiveData: LiveData<List<Cafe>>
    suspend fun refreshCafeList()
    fun getCafeById(cafeId: String): LiveData<Cafe>
    suspend fun getCafeEntityByDistrict(districtId: String): CafeEntity
}