package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
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

@Composable
fun WarningCard(
    title: String,
    @DrawableRes icon: Int,
    iconDescription: String,
    modifier: Modifier = Modifier,
    cardColors: CardColors = FoodDeliveryCardDefaults.warningCardStatusColors
) {
    FoodDeliveryCard(
        modifier = modifier,
        colors = cardColors,
        shape = infoCardShape,
        clickable = false
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
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
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.statusColors.onStatus
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
            icon = R.drawable.ic_discount,
            iconDescription = "Иконка"
        )
    }
}
