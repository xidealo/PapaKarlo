package com.bunbeauty.menu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.core.extension.getOrderColor
import com.bunbeauty.core.extension.getOrderStatusName
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.designsystem.ui.element.OrderStatusChip
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.title_menu_last_order

@Composable
internal fun LastOrderMenuItem(
    modifier: Modifier = Modifier,
    lastOrder: LightOrder,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        onClick = onClick,
        elevated = false,
        shape = RoundedCornerShape(0.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        horizontal = 16.dp,
                    ).padding(
                        top = 8.dp,
                    ),
        ) {
            Text(
                text = stringResource(Res.string.title_menu_last_order),
                modifier =
                    Modifier
                        .requiredWidthIn(min = FoodDeliveryTheme.dimensions.codeWidth)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = lastOrder.code,
                    modifier =
                        Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                            .weight(1f),
                    style = FoodDeliveryTheme.typography.titleLarge.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )

                OrderStatusChip(
                    statusName = lastOrder.status.getOrderStatusName(),
                    background = lastOrder.status.getOrderColor(),
                )
            }

            FoodDeliveryHorizontalDivider(modifier = Modifier.padding(top = 24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LastOrderMenuItemPreview() {
    FoodDeliveryTheme {
        LastOrderMenuItem(
            lastOrder =
                LightOrder(
                    uuid = "uuid",
                    status = com.bunbeauty.core.model.order.OrderStatus.DONE,
                    code = "A-123",
                    dateTime =
                        com.bunbeauty.core.model.date_time.DateTime(
                            date =
                                com.bunbeauty.core.model.date_time.Date(
                                    dayOfMonth = 1,
                                    monthNumber = 1,
                                    year = 2024,
                                ),
                            time =
                                com.bunbeauty.core.model.date_time.Time(
                                    hours = 12,
                                    minutes = 0,
                                ),
                        ),
                ),
            onClick = {},
        )
    }
}
