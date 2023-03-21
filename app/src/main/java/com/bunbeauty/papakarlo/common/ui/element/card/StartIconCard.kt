package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun StartIconCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @DrawableRes iconId: Int,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    @StringRes labelStringId: Int,
    onClick: (() -> Unit) = {}
) {
    RowCard(
        modifier = modifier,
        enabled = enabled,
        elevated = elevated,
        startIconId = iconId,
        startIconTint = iconTint,
        labelStringId = labelStringId,
        onClick = onClick,
    )
}

@Composable
fun StartIconCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @DrawableRes iconId: Int,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    label: String,
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

@Preview@Preview(showSystemUi = true)
@Composable
fun IconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            iconId = R.drawable.ic_info,
            labelStringId = R.string.title_about_app
        )
    }
}

@Preview@Preview(showSystemUi = true)
@Composable
fun OriginalColorIconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            iconId = R.drawable.ic_bb,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
            labelStringId = R.string.title_about_app
        )
    }
}
