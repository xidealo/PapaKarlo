package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults.infoCardShape
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_discount

@Composable
fun BannerCard(
    title: String,
    text: String,
    icon: DrawableResource,
    iconDescription: String,
    modifier: Modifier = Modifier,
    cardColors: CardColors = FoodDeliveryCardDefaults.positiveCardStatusColors,
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = cardColors,
        shape = infoCardShape,
        clickable = false,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
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
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.statusColors.onStatus,
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = text,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.statusColors.onStatus,
            )
        }
    }
}

@Preview
@Composable
fun BannerCardPreview() {
    FoodDeliveryTheme {
        BannerCard(
            title = "Скидка 10%",
            text = "Успей сделать первый заказ со скидкой 10%",
            icon = Res.drawable.ic_discount,
            iconDescription = "Скидка",
        )
    }
}
