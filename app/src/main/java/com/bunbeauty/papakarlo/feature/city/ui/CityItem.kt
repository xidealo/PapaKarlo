package com.bunbeauty.papakarlo.feature.city.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    cityName: String,
    hasShadow: Boolean = true,
    onClick: () -> Unit
) {
    val elevation = if (hasShadow) {
        FoodDeliveryTheme.dimensions.elevation
    } else {
        0.dp
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
            .shadow(
                elevation = elevation,
                shape = mediumRoundedCornerShape
            )
            .clip(mediumRoundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        colors = FoodDeliveryTheme.colors.cardColors()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            text = cityName,
            style = FoodDeliveryTheme.typography.body1,
            color = FoodDeliveryTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun CityItemPreview() {
    CityItem(cityName = "Москва") {}
}
