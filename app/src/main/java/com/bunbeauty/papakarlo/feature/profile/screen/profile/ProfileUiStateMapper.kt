package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.profile.ProfileState

class ProfileUiStateMapper(
    private val stringUtil: IStringUtil,
    private val orderItemMapper: OrderItemMapper,
) {

    fun map(profileState: ProfileState): ProfileUi {
        return ProfileUi(
            topCartUi = TopCartUi(
                cost = profileState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = profileState.cartCostAndCount?.count ?: ""
            ),
            orderItem = profileState.lastOrder?.let {
                orderItemMapper.toItem(it)
            },
            state = profileState.state,
        )
    }
}
