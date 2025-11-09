package com.bunbeauty.shared.ui.common.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.bunbeauty.shared.ui.common.ui.element.CircularProgressBar
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme

@Composable
internal fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    FoodDeliveryTheme {
        LoadingScreen()
    }
}
