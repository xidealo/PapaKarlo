package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi

data class CafeListUi(
    val cafeList: List<CafeItem> = emptyList(),
    val topCartUi: TopCartUi,
    val state: CafeListState.State = CafeListState.State.Loading,
    val eventList: List<CafeListState.Event> = emptyList(),
)
