package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState

@Composable
fun getCafeStatusColor(cafeStatus: CafeOpenState): Color =
    when (cafeStatus) {
        is CafeOpenState.Opened -> FoodDeliveryTheme.colors.statusColors.positive
        is CafeOpenState.CloseSoon -> FoodDeliveryTheme.colors.statusColors.warning
        is CafeOpenState.Closed -> FoodDeliveryTheme.colors.statusColors.negative
    }
