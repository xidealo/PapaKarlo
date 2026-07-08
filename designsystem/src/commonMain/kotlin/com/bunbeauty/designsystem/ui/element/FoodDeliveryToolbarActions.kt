package com.bunbeauty.designsystem.ui.element

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed interface FoodDeliveryToolbarActions

class FoodDeliveryAction(
    val iconId: DrawableResource,
    val onClick: () -> Unit,
    val tint: Color? = null,
    val contentDescriptionId: StringResource? = null,
) : FoodDeliveryToolbarActions

class FoodDeliveryCartAction(
    val topCartUi: TopCartUi,
    val onClick: () -> Unit,
) : FoodDeliveryToolbarActions
