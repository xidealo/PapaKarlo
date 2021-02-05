package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import kotlinx.coroutines.Deferred

interface CafeRepo {
    val cafeEntityListLiveData: LiveData<List<Cafe>>
    suspend fun refreshCafeList()
    fun getCafeById(cafeId: String): LiveData<Cafe>
    suspend fun getCafeEntityByDistrict(districtId: String): Deferred<CafeEntity>
}