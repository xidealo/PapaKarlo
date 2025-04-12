package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.zeroCardShape
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun TextCardWithDivider(
    label: String,
    modifier: Modifier = Modifier,
    value: String? = null
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = false,
        elevated = false,
        shape = zeroCardShape
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        text = label,
                        style = if (value == null) {
                            FoodDeliveryTheme.typography.bodyLarge
                        } else {
                            FoodDeliveryTheme.typography.labelSmall
                        },
                        color = if (value == null) {
                            FoodDeliveryTheme.colors.mainColors.onSurface
                        } else {
                            FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                        }
                    )
                    value?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = value,
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                }
            }

            FoodDeliveryHorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TextCardPreview() {
    FoodDeliveryTheme {
        TextCardWithDivider(
            label = "Номер телефона",
            value = "+7 999 000-00-00"
        )
    }
}
