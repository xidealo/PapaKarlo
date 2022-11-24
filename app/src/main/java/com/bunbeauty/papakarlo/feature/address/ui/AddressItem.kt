package com.bunbeauty.papakarlo.feature.address.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: String,
    isClickable: Boolean,
    onClick: (() -> Unit)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
            .clip(mediumRoundedCornerShape)
            .clickable(
                enabled = isClickable,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            text = address,
            style = FoodDeliveryTheme.typography.body1,
            color = FoodDeliveryTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun AddressItemPreview() {
    AddressItem(
        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
        isClickable = false
    ) {}
}
