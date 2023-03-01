package com.bunbeauty.papakarlo.common.ui.toolbar

import androidx.annotation.DrawableRes


sealed class FoodDeliveryToolbarActions

class FoodDeliveryAction(
    @DrawableRes val iconId: Int,
    val onClick: () -> Unit,
): FoodDeliveryToolbarActions()

class FoodDeliveryCartAction(
    val count: String,
    val cost: String,
    val onClick: () -> Unit,
): FoodDeliveryToolbarActions()