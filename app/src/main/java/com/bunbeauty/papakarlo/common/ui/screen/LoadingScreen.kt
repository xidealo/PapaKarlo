package com.bunbeauty.papakarlo.common.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
internal fun LoadingScreen(
    background: Color = FoodDeliveryTheme.colors.background
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        CircularProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}
