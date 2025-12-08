package com.bunbeauty.designsystem.ui.element

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme

@Composable
fun CircularProgressBar(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = FoodDeliveryTheme.colors.mainColors.primary,
    )
}
