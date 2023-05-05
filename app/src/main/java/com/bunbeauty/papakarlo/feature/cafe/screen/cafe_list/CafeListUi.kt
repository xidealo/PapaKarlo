package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.presentation.cafe_list.CafeListState

data class CafeListUi(
    val cafeList: List<CafeItemAndroid> = emptyList(),
    val topCartUi: TopCartUi,
    val state: CafeListState.State = CafeListState.State.Loading,
)
