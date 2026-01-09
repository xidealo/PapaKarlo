package com.bunbeauty.shared.ui.screen.cafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.core.model.cafe.CafeOpenState

@Composable
fun getCafeStatusColor(cafeStatus: CafeOpenState): Color =
    when (cafeStatus) {
        is CafeOpenState.Opened -> FoodDeliveryTheme.colors.statusColors.positive
        is CafeOpenState.CloseSoon -> FoodDeliveryTheme.colors.statusColors.warning
        is CafeOpenState.Closed -> FoodDeliveryTheme.colors.statusColors.negative
    }
