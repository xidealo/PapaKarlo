package com.bunbeauty.shared.ui.common.ui.element.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.shared.ui.common.ui.element.card.FoodDeliveryCardDefaults.zeroCardShape
import com.bunbeauty.shared.ui.common.ui.icon16
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.ic_right_arrow

@Composable
fun NavigationCardWithDivider(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    value: String? = null,
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        elevated = false,
        onClick = onClick,
        shape = zeroCardShape,
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                ) {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                        text = label,
                        style =
                            if (value == null) {
                                FoodDeliveryTheme.typography.bodyLarge
                            } else {
                                FoodDeliveryTheme.typography.labelSmall
                            },
                        color =
                            if (value == null) {
                                FoodDeliveryTheme.colors.mainColors.onSurface
                            } else {
                                FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                            },
                    )
                    value?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = value,
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                        )
                    }
                }
                Icon(
                    modifier = Modifier.icon16(),
                    painter = painterResource(Res.drawable.ic_right_arrow),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null,
                )
            }

            FoodDeliveryHorizontalDivider(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp),
            )
        }
    }
}

@Preview
@Composable
private fun NavigationCardWithDividerPreview() {
    FoodDeliveryTheme {
        NavigationCardWithDivider(
            label = "Название раздела",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun NavigationCardWithDividerWithValuePreview() {
    FoodDeliveryTheme {
        NavigationCardWithDivider(
            label = "Название раздела",
            value = "sadasdasd",
            onClick = {},
        )
    }
}
