package com.bunbeauty.designsystem.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.bunbeauty.designsystem.ui.element.CircularProgressBar

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressBar(
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    FoodDeliveryTheme {
        LoadingScreen()
    }
}
