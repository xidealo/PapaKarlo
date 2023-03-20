package com.bunbeauty.papakarlo.feature.address.ui

import androidx.compose.foundation.layout.Row
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
    elevated: Boolean,
    isSelected: Boolean = false,
    onClick: (() -> Unit),
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = isClickable,
        elevated = elevated
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = address,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .padding(start = FoodDeliveryTheme.dimensions.smallSpace)
                        .size(16.dp)
                        .align(CenterVertically),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                    contentDescription = stringResource(R.string.description_ic_checked),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddressItemPreview() {
    FoodDeliveryTheme {
        FoodDeliveryCard {
            AddressItem(
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                isClickable = false,
                elevated = false
            ) {}
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddressItemSelectedPreview() {
    FoodDeliveryTheme {
        FoodDeliveryCard {
            AddressItem(
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
                isClickable = false,
                elevated = false,
                isSelected = true
            ) {}
        }
    }
}
