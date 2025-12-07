package com.bunbeauty.designsystem.ui.element

import org.jetbrains.compose.resources.DrawableResource

sealed interface FoodDeliveryToolbarActions

class FoodDeliveryAction(
    val iconId: DrawableResource,
    val onClick: () -> Unit,
) : FoodDeliveryToolbarActions

class FoodDeliveryCartAction(
    val topCartUi: TopCartUi,
    val onClick: () -> Unit,
) : FoodDeliveryToolbarActions
