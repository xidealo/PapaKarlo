package com.bunbeauty.papakarlo.feature.address.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: String,
    isClickable: Boolean,
    hasShadow: Boolean,
    isSelected: Boolean = false,
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
        Row {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(start = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                text = address,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.onSurface
            )
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(16.dp)
                        .align(CenterVertically),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    contentDescription = stringResource(R.string.description_ic_checked),
                    tint = FoodDeliveryTheme.colors.onSurfaceVariant
                )
            }
        }
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

@Preview
@Composable
fun AddressItemSelectedPreview() {
    AddressItem(
        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
        isClickable = false,
        hasShadow = false,
        isSelected = true
    ) {}
}
