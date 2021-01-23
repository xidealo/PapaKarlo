package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity

interface CafeRepo {
    val cafeEntityListLiveData: LiveData<List<CafeEntity>>
    suspend fun refreshCafeList()
}