package com.bunbeauty.papakarlo.common.ui.element.selectable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun SelectableItem(
    title: String,
    clickable: Boolean,
    elevated: Boolean,
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        clickable = clickable,
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
                text = title,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .padding(start = FoodDeliveryTheme.dimensions.smallSpace)
                        .icon16()
                        .align(CenterVertically),
                    painter = painterResource(R.drawable.ic_check),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = stringResource(R.string.description_ic_checked)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddressItemPreview() {
    FoodDeliveryTheme {
        SelectableItem(
            title = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
            clickable = false,
            elevated = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun AddressItemSelectedPreview() {
    FoodDeliveryTheme {
        SelectableItem(
            title = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж, код домофона 555",
            clickable = false,
            elevated = false,
            isSelected = true,
            onClick = {}
        )
    }
}
