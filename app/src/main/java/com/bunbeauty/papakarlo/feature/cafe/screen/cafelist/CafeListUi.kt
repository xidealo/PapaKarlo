package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.cafe_list.CafeListState

data class CafeListUi(
    val cafeList: List<CafeItemAndroid> = emptyList(),
    val topCartUi: TopCartUi,
    val state: CafeListState.State = CafeListState.State.Loading
)
