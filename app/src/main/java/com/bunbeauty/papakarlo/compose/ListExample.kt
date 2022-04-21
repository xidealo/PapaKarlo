package com.bunbeauty.papakarlo.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme

@Composable
fun ListExample(enemies: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(enemies) { item ->
            Text(
                modifier = Modifier.padding(16.dp),
                style = FoodDeliveryTheme.typography.h1,
                color = FoodDeliveryTheme.colors.bunBeautyBrandColor,
                text = item
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListExamplePreview() {
    ListExample(
        listOf(
            "Смит",
            "Патапчик",
            "Путин",
        )
    )
}