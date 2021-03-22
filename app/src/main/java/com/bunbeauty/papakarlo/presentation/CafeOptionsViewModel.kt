package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.LiveData
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.domain.repository.cafe.CafeRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import javax.inject.Inject

class CafeOptionsViewModel @Inject constructor(private val cafeRepo: CafeRepo): BaseViewModel() {

    fun getCafeLiveData(cafeId: String): LiveData<Cafe> {
        return cafeRepo.getCafeById(cafeId)
    }
}