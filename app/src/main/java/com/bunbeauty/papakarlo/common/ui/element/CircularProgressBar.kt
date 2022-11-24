package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = FoodDeliveryTheme.colors.primary
    )
}
