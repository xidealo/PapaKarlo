package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.infoCardShape
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold

@Composable
fun BannerCard(
    title: String,
    text: String,
    @DrawableRes icon: Int,
    iconDescription: String,
    modifier: Modifier = Modifier,
    cardColors: CardColors = FoodDeliveryCardDefaults.positiveCardStatusColors,
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = cardColors,
        shape = infoCardShape,
        clickable = false
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(icon),
                    tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                    contentDescription = iconDescription
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = title,
                    style = FoodDeliveryTheme.typography.titleMedium.bold,
                    color = FoodDeliveryTheme.colors.statusColors.onStatus
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = text,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.statusColors.onStatus
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
            icon = R.drawable.ic_discount,
            iconDescription = "Скидка"
        )
    }
}
