package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    text: String,
    elevated: Boolean = true,
    onClick: () -> Unit = {},
    clickable: Boolean = true
) {
    FoodDeliveryCard(
        modifier = modifier,
        elevated = elevated,
        onClick = onClick,
        clickable = clickable
    ) {
        OverflowingText(
            modifier = Modifier
                .padding(
                    horizontal = FoodDeliveryTheme.dimensions.cardLargeInnerSpace,
                    vertical = FoodDeliveryTheme.dimensions.cardMediumInnerSpace
                )
                .fillMaxWidth(),
            text = text,
            style = FoodDeliveryTheme.typography.bodyLarge,
            color = FoodDeliveryTheme.colors.mainColors.onSurface
        )
    }
}

@Preview
@Composable
private fun SimpleCardPreview() {
    FoodDeliveryTheme {
        SimpleCard(
            modifier = Modifier,
            text = "Text",
            onClick = {}
        )
    }
}
