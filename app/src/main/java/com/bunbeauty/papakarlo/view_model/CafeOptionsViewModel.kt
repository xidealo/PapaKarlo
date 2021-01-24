package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(private val cafeRepo: CafeRepo): BaseViewModel() {

    fun getCafeLiveData(cafeId: String): LiveData<Cafe> {
        return cafeRepo.getCafeById(cafeId)
    }
}