package com.bunbeauty.shared.ui.screen.menu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.description_ic_discount
import papakarlo.shared.generated.resources.ic_discount
import papakarlo.shared.generated.resources.msg_menu_discount
import papakarlo.shared.generated.resources.title_menu_discount

@Composable
fun FirstOrderDiscountItem(
    modifier: Modifier = Modifier,
    discount: String,
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = FoodDeliveryCardDefaults.positiveCardStatusColors,
        clickable = false,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(Res.drawable.ic_discount),
                    tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                    contentDescription = stringResource(Res.string.description_ic_discount),
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(resource = Res.string.title_menu_discount, discount),
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.statusColors.onStatus,
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(resource = Res.string.msg_menu_discount, discount),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.statusColors.onStatus,
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
