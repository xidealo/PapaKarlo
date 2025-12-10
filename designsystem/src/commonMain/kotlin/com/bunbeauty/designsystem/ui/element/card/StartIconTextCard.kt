package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_info

@Composable
fun StartIconTextCard(
    hint: String,
    label: String,
    iconId: DrawableResource,
    modifier: Modifier = Modifier,
    elevated: Boolean = true,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    iconDescription: String? = null,
    onClick: (() -> Unit) = {},
) {
    FoodDeliveryCard(
        modifier = modifier,
        elevated = elevated,
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                        vertical = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                tint = iconTint,
                contentDescription = iconDescription,
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
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
}

@Preview
@Composable
private fun StartIconTextCardPreview() {
    FoodDeliveryTheme {
        StartIconTextCard(
            hint = "Информация",
            label = "Важная информация",
            iconId = Res.drawable.ic_info,
        )
    }
}

@Preview
@Composable
private fun StartIconTextCardLongContentPreview() {
    FoodDeliveryTheme {
        StartIconTextCard(
            hint = "Длиииииииииииииииииииииииииииииииииииииииииииииииинный текст",
            label = "Длиииииииииииииииииииииииииииииииииииииииииииииииинный текст",
            iconId = Res.drawable.ic_info,
        )
    }
}
