package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun BlurLine(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(FoodDeliveryTheme.dimensions.blurHeight)
            .background(FoodDeliveryTheme.colors.surfaceGradient)
    )
}