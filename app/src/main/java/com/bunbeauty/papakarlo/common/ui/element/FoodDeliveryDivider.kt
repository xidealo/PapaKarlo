package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun FoodDeliveryDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = FoodDeliveryTheme.colors.mainColors.stroke
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
