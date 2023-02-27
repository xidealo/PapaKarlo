package com.bunbeauty.papakarlo.feature.address.ui

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: String,
    isClickable: Boolean,
    hasShadow: Boolean,
    onClick: (() -> Unit),
) {
    FoodDeliveryCard(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.cardHeight),
        onClick = onClick,
        enabled = isClickable,
        elevated = false
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
        isClickable = false,
        hasShadow = false
    ) {}
}
