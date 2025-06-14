package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.mapper.OrderItemMapper
import com.bunbeauty.shared.presentation.profile.ProfileState

class ProfileUiStateMapper(
    private val orderItemMapper: OrderItemMapper
) {

    fun map(profileState: ProfileState): ProfileUi {
        return ProfileUi(
            orderItem = profileState.lastOrder?.let {
                orderItemMapper.toItem(it)
            },
            state = profileState.state
        )
    }
}
