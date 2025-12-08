package com.bunbeauty.shared.ui.screen.city.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.icon16
import papakarlo.shared.generated.resources.description_ic_checked
import papakarlo.shared.generated.resources.ic_check

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    cityName: String,
    elevated: Boolean = true,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier =
            modifier
                .fillMaxWidth(),
        onClick = onClick,
        elevated = elevated,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp),
        ) {
            Text(
                modifier =
                    Modifier
                        .weight(1f),
                text = cityName,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )

            if (isSelected) {
                Icon(
                    modifier =
                        Modifier
                            .padding(start = FoodDeliveryTheme.dimensions.smallSpace)
                            .icon16()
                            .align(Alignment.CenterVertically),
                    painter = painterResource(Res.drawable.ic_check),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = stringResource(Res.string.description_ic_checked),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CityItemPreview() {
    FoodDeliveryTheme {
        CityItem(
            cityName = "Москва",
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedCityItemPreview() {
    FoodDeliveryTheme {
        CityItem(
            cityName = "Москва",
            isSelected = true,
            onClick = {},
        )
    }
}
