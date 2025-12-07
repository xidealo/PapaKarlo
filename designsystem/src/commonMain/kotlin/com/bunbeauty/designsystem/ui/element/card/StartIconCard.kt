package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_bb
import papakarlo.designsystem.generated.resources.ic_info

@Composable
fun StartIconCard(
    label: String,
    iconId: DrawableResource,
    modifier: Modifier = Modifier,
    elevated: Boolean = true,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    onClick: (() -> Unit) = {},
    clickable: Boolean = true,
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        elevated = elevated,
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                tint = iconTint,
                contentDescription = label,
            )
            _root_ide_package_.com.bunbeauty.designsystem.ui.element.OverflowingText(
                modifier = Modifier.padding(start = 16.dp),
                text = label,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
        }
    }
}

@Preview
@Composable
private fun IconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Ооооооооооооооочень длинная информация",
            iconId = Res.drawable.ic_info,
        )
    }
}

@Preview
@Composable
private fun OriginalColorIconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Информация",
            iconId = Res.drawable.ic_bb,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        )
    }
}
