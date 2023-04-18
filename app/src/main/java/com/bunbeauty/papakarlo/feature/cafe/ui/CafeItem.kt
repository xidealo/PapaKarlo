package com.bunbeauty.papakarlo.feature.cafe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.shared.presentation.cafe_list.CafeItem

@Composable
fun CafeItem(
    modifier: Modifier = Modifier,
    cafeItem: CafeItem,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = cafeItem.address,
                modifier = Modifier.fillMaxWidth(),
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
            Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                Text(
                    text = cafeItem.workingHours,
                    style = FoodDeliveryTheme.typography.labelMedium.medium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                )

                Text(
                    modifier = Modifier
                        .padding(start = FoodDeliveryTheme.dimensions.smallSpace),
                    text = getCafeStatusText(cafeItem.cafeOpenState),
                    style = FoodDeliveryTheme.typography.labelMedium.medium,
                    color = getCafeStatusColor(cafeItem.cafeOpenState),
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CafeItemOpenPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem = CafeItem(
                uuid = "",
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                workingHours = "9:00 - 22:00",
                cafeOpenState =  CafeItem.CafeOpenState.Opened,
            ),
            onClick = {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CafeItemCloseSoonPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem = CafeItem(
                uuid = "",
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                workingHours = "9:00 - 22:00",
                cafeOpenState =CafeItem.CafeOpenState.CloseSoon(30),
            ),
            onClick = {},
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CafeItemClosedPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem = CafeItem(
                uuid = "",
                address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                workingHours = "9:00 - 22:00",
                cafeOpenState = CafeItem.CafeOpenState.Closed,
            ),
            onClick = {},
        )
    }
}
