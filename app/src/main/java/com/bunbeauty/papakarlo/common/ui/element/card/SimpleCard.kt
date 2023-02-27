package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.card
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    text: String,
    hasShadow: Boolean = true,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier
            .card(),
        onClick = onClick,
        colors = FoodDeliveryTheme.colors.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OverflowingText(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
fun SimpleCardPreview() {
    SimpleCard(
        text = "Text"
    ) {}
}
