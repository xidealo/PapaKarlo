package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun StartIconCard(
    label: String,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    onClick: (() -> Unit) = {}
) {
    RowCard(
        modifier = modifier,
        enabled = enabled,
        elevated = elevated,
        startIconId = iconId,
        startIconTint = iconTint,
        label = label,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun IconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Информация",
            iconId = R.drawable.ic_info,
        )
    }
}

@Preview
@Composable
private fun OriginalColorIconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Информация",
            iconId = R.drawable.ic_bb,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        )
    }
}
