package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.presentation.cafe_list.CafeItem

@Composable
fun getCafeStatusColor(cafeStatus: CafeItem.CafeOpenState): Color {
    return when (cafeStatus) {
        is CafeItem.CafeOpenState.Opened -> FoodDeliveryTheme.colors.statusColors.positive
        is CafeItem.CafeOpenState.CloseSoon -> FoodDeliveryTheme.colors.statusColors.warning
        is CafeItem.CafeOpenState.Closed -> FoodDeliveryTheme.colors.statusColors.negative
    }
}