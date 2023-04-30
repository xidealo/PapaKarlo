package com.bunbeauty.papakarlo.feature.profile.screen.profile

import com.bunbeauty.papakarlo.feature.order.model.OrderItem
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.presentation.profile.ProfileState

data class ProfileUi(
    val orderItem: OrderItem? = null,
    val state: ProfileState.State = ProfileState.State.LOADING,
    val topCartUi: TopCartUi
)
