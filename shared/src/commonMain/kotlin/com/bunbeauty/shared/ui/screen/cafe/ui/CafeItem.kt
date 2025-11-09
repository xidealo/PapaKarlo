package com.bunbeauty.shared.ui.screen.cafe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import com.bunbeauty.shared.ui.theme.medium
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState

@Composable
fun CafeItem(
    modifier: Modifier = Modifier,
    cafeItem: CafeItemAndroid,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RectangleShape,
        elevated = false,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp),
        ) {
            Text(
                text = cafeItem.address,
                modifier = Modifier.fillMaxWidth(),
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
            Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                Text(
                    text = cafeItem.workingHours,
                    style = FoodDeliveryTheme.typography.labelMedium.medium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                )

                Text(
                    modifier =
                        Modifier
                            .padding(start = FoodDeliveryTheme.dimensions.smallSpace),
                    text = cafeItem.cafeStatusText,
                    style = FoodDeliveryTheme.typography.labelMedium.medium,
                    color = getCafeStatusColor(cafeItem.cafeOpenState),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CafeItemOpenPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem =
                CafeItemAndroid(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    phone = "00000000",
                    cafeOpenState = CafeOpenState.Opened,
                    cafeStatusText = "Open",
                    isLast = true,
                ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CafeItemCloseSoonPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem =
                CafeItemAndroid(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    phone = "00000000",
                    cafeOpenState = CafeOpenState.CloseSoon(minutesUntil = 30),
                    cafeStatusText = "Closed soon 30 min",
                    isLast = true,
                ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CafeItemClosedPreview() {
    FoodDeliveryTheme {
        CafeItem(
            cafeItem =
                CafeItemAndroid(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    phone = "00000000",
                    cafeOpenState = CafeOpenState.Closed,
                    cafeStatusText = "Closed",
                    isLast = true,
                ),
            onClick = {},
        )
    }
}
