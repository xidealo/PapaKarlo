package com.bunbeauty.papakarlo.common.ui.toolbar

import androidx.annotation.DrawableRes
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi

sealed class FoodDeliveryToolbarActions

class FoodDeliveryAction(
    @DrawableRes val iconId: Int,
    val onClick: () -> Unit,
): FoodDeliveryToolbarActions()

class FoodDeliveryCartAction(
    val topCartUi: TopCartUi,
    val onClick: () -> Unit,
): FoodDeliveryToolbarActions()