package com.bunbeauty.shared.ui.common.ui.element.topbar

import com.bunbeauty.shared.ui.screen.topcart.TopCartUi
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
