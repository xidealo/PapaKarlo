package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    onClick: (() -> Unit) = {},
    colors: CardColors = FoodDeliveryTheme.colors.cardColors(),
    shape: Shape = mediumRoundedCornerShape,
    content: @Composable ColumnScope.() -> Unit,
) {
    val elevation = if (elevated) 1.dp else 0.dp
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = CardDefaults.cardElevation(elevation),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}
