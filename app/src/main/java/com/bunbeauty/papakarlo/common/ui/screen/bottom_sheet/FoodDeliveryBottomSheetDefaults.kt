package com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryBottomSheetDefaults {

    val bottomSheetShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(
            topStart = FoodDeliveryTheme.dimensions.bottomSheetRadius,
            topEnd = FoodDeliveryTheme.dimensions.bottomSheetRadius,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
}
