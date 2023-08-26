package com.bunbeauty.papakarlo.common.ui.element.topbar

import androidx.annotation.DrawableRes
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi

sealed interface FoodDeliveryToolbarActions

class FoodDeliveryAction(
    @DrawableRes val iconId: Int,
    val onClick: () -> Unit
) : FoodDeliveryToolbarActions

class FoodDeliveryCartAction(
    val topCartUi: TopCartUi,
    val onClick: () -> Unit
) : FoodDeliveryToolbarActions
