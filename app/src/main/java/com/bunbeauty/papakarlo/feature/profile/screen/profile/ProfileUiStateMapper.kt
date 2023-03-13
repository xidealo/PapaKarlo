package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.profile.ProfileState

class ProfileUiStateMapper(
    private val stringUtil: IStringUtil,
    private val orderItemMapper: OrderItemMapper,
) {
    fun map(cafeListState: ProfileState): ProfileUiState {
        return ProfileUiState(
            topCartUi = TopCartUi(
                cost = cafeListState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = cafeListState.cartCostAndCount?.count ?: ""
            ),
            orderItem = cafeListState.lastOrder?.let {
                orderItemMapper.toItem(it)
            },
            state = cafeListState.state,
            eventList = cafeListState.eventList,
        )
    }
}
