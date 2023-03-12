package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.domain.model.cafe.CafeStatus

@Composable
fun getCafeStatusColor(cafeStatus: CafeStatus): Color {
    return when (cafeStatus) {
        CafeStatus.OPEN -> FoodDeliveryTheme.colors.statusColors.positive
        CafeStatus.CLOSE_SOON -> FoodDeliveryTheme.colors.statusColors.warning
        CafeStatus.CLOSED -> FoodDeliveryTheme.colors.statusColors.negative
    }
}