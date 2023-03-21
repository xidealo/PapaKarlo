package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.getCardElevation
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    onClick: (() -> Unit) = {},
    colors: CardColors = FoodDeliveryCardDefaults.cardColors,
    shape: Shape = mediumRoundedCornerShape,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false,
    ) {
        Card(
            modifier = modifier,
            shape = shape,
            colors = colors,
            elevation = getCardElevation(elevated),
            enabled = enabled,
            onClick = onClick,
            border = border,
            content = content
        )
    }
}
