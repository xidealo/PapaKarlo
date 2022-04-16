package com.bunbeauty.papakarlo.compose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar

@Composable
internal fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressBar(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}