package com.bunbeauty.papakarlo.common.ui.element.surface

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun FoodDeliverySurface(
    modifier: Modifier = Modifier,
    color: Color = FoodDeliveryTheme.colors.mainColors.surface,
    elevated: Boolean = true,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.zIndex(1f),
        color = color,
        shadowElevation = FoodDeliverySurfaceDefaults.getSurfaceElevation(elevated),
        content = content
    )
}
