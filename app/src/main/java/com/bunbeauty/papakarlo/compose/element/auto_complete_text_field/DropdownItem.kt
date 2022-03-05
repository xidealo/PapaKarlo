package com.bunbeauty.papakarlo.compose.element.auto_complete_text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.compose.element.OverflowingText
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(FoodDeliveryTheme.dimensions.cardHeight)
            .clip(mediumRoundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
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