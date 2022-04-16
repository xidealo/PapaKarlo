package com.bunbeauty.papakarlo.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.compose.card
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme

@Composable
internal fun ErrorScreen(message: String, onClick: (() -> Unit)? = null) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                .card(true)
                .align(Alignment.Center)
                .clickable(
                    enabled = onClick != null,
                    onClick = onClick ?: {}
                ),
            backgroundColor = FoodDeliveryTheme.colors.surface
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace),
                text = message,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen("Ошибка загрузки")
}