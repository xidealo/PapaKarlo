package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold

@Composable
fun FirstOrderDiscountItem(
    modifier: Modifier = Modifier,
    discount: String,
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = FoodDeliveryCardDefaults.cardStatusColors,
        clickable = false
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(R.drawable.ic_discount),
                    tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                    contentDescription = stringResource(R.string.description_ic_discount)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(id = R.string.title_menu_discount, discount),
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.statusColors.onStatus
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.msg_menu_discount, discount),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.statusColors.onStatus
            )
        }
    }
}

@Preview
@Composable
fun FirstOrderDiscountItemPreview() {
    FoodDeliveryTheme {
        FirstOrderDiscountItem(discount = "10%")
    }
}
