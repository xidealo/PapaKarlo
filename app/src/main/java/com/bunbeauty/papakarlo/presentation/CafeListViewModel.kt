package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.data.mapper.adapter.CafeAdapterMapper
import com.bunbeauty.domain.model.adapter.CafeAdapterModel
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val cafeAdapterMapper: CafeAdapterMapper
) : ToolbarViewModel() {


    val cafeListFlow by lazy {
        cafeRepo.cafeEntityListFlow.map { cafeList -> cafeList.map { cafeAdapterMapper.from(it) }  }
    }

    fun onCafeCardClick(cafeAdapterModel: CafeAdapterModel) {
        router.navigate(toCafeOptionsBottomSheet(cafeAdapterModel))
    }
}