package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.getCardElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryCard(
    modifier: Modifier = Modifier,
    elevated: Boolean = true,
    onClick: (() -> Unit) = {},
    clickable: Boolean = true,
    colors: CardColors = FoodDeliveryCardDefaults.cardColors,
    shape: Shape = FoodDeliveryCardDefaults.cardShape,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides 0.dp
    ) {
        if (clickable) {
            Card(
                modifier = modifier,
                shape = shape,
                colors = colors,
                elevation = getCardElevation(elevated),
                onClick = onClick,
                border = border,
                content = content
            )
        } else {
            Card(
                modifier = modifier,
                shape = shape,
                colors = colors,
                elevation = getCardElevation(elevated),
                border = border,
                content = content
            )
        }
    }
}
