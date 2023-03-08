package com.bunbeauty.papakarlo.common.ui.element

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.common.ui.theme.orderStatusCornerShape
import com.bunbeauty.shared.domain.model.order.OrderStatus

@Composable
fun StatusChip(
    modifier: Modifier = Modifier,
    orderStatus: OrderStatus,
    statusName: String,
) {
    Surface(
        modifier = modifier
            .clip(orderStatusCornerShape),
        color = FoodDeliveryTheme.colors.orderColor(orderStatus)
    ) {
        Text(
            text = statusName,
            modifier = Modifier
                .padding(
                    vertical = FoodDeliveryTheme.dimensions.verySmallSpace,
                    horizontal = FoodDeliveryTheme.dimensions.smallSpace
                ),
            style = FoodDeliveryTheme.typography.labelSmall.medium,
            color = FoodDeliveryTheme.colors.onStatus,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun StatusChipPreview() {
    StatusChip(
        orderStatus = OrderStatus.NOT_ACCEPTED,
        statusName = "Обрабатывается"
    )
}
