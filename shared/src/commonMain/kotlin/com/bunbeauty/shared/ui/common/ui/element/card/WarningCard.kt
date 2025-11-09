package com.bunbeauty.shared.ui.common.ui.element.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCardDefaults.infoCardShape
import com.bunbeauty.shared.ui.common.ui.icon24
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import org.jetbrains.compose.resources.DrawableResource
import papakarlo.shared.generated.resources.ic_discount

@Composable
fun WarningCard(
    title: String,
    icon: DrawableResource,
    iconDescription: String,
    modifier: Modifier = Modifier,
    cardColors: CardColors = FoodDeliveryCardDefaults.warningCardStatusColors,
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = cardColors,
        shape = infoCardShape,
        clickable = false,
    ) {
        Row(
            modifier =
                Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(icon),
                tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                contentDescription = iconDescription,
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.statusColors.onStatus,
            )
        }
    }
}

@Preview
@Composable
fun WarningCardPreview() {
    FoodDeliveryTheme {
        WarningCard(
            title = "Скидка 10%",
            icon = Res.drawable.ic_discount,
            iconDescription = "Иконка",
        )
    }
}
