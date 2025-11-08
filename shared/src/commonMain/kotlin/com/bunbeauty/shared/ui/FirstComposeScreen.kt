package com.bunbeauty.shared.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FirstComposeScreen() {
    Text(
        "Some text"
    )
}

@Preview
@Composable
internal fun FirstComposeScreenPreview() {
    FirstComposeScreen()
}