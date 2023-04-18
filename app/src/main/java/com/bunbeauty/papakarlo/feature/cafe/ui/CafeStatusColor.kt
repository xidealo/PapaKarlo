package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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

@Composable
fun getCafeStatusText(cafeStatus: CafeItem.CafeOpenState): String {
    return when (cafeStatus) {
        is CafeItem.CafeOpenState.Opened -> stringResource(R.string.msg_cafe_open)
        is CafeItem.CafeOpenState.CloseSoon -> {
            stringResource(R.string.msg_cafe_close_soon) +
                    cafeStatus.time  +
                    getMinuteString(cafeStatus.time)
        }
        is CafeItem.CafeOpenState.Closed -> stringResource(R.string.msg_cafe_closed)
    }
}

@Composable
private fun getMinuteString(closeIn: Int): String {
    val minuteStringId = when {
        (closeIn / 10 == 1) -> R.string.msg_cafe_minutes
        (closeIn % 10 == 1) -> R.string.msg_cafe_minute
        (closeIn % 10 in 2..4) -> R.string.msg_cafe_minutes_234
        else -> R.string.msg_cafe_minutes
    }
    return stringResource(minuteStringId)
}