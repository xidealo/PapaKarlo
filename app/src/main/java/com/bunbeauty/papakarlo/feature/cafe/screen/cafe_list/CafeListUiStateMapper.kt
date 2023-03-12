package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState

class CafeListUiStateMapper(
    private val stringUtil: IStringUtil,
) {
    fun map(cafeListState: CafeListState): CafeListUi {
        return CafeListUi(
            topCartUi = TopCartUi(
                cost = cafeListState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = cafeListState.cartCostAndCount?.count ?: ""
            ),
            cafeList = cafeListState.cafeList,
            state = cafeListState.state,
            eventList = cafeListState.eventList,
        )
    }
}
