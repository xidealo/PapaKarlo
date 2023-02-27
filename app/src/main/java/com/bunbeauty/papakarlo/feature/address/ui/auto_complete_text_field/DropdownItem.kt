package com.bunbeauty.papakarlo.feature.address.ui.auto_complete_text_field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier
            .fillMaxWidth()
            .height(FoodDeliveryTheme.dimensions.cardHeight),
        onClick = onClick,
        elevated = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            OverflowingText(
                modifier = Modifier
                    .padding(start = FoodDeliveryTheme.dimensions.mediumSpace)
                    .align(Alignment.CenterStart),
                text = text,
                style = FoodDeliveryTheme.typography.body1,
                color = FoodDeliveryTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun DropdownItemPreview() {
    DropdownItem(text = "Улица Ленина") {}
}
