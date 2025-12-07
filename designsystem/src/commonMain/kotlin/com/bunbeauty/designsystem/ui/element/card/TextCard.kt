package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    hint: String,
    label: String,
    elevated: Boolean = true,
) {
    FoodDeliveryCard(
        modifier = modifier,
        elevated = elevated,
        clickable = false,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                        vertical = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
        ) {
            Text(
                text = hint,
                style = FoodDeliveryTheme.typography.labelSmall.medium,
                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            )
            Text(
                text = label,
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
        }
    }
}

@Preview
@Composable
private fun TextCardPreview() {
    FoodDeliveryTheme {
        TextCard(
            hint = "Номер телефона",
            label = "+7 999 000-00-00",
        )
    }
}
