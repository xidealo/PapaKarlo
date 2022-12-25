package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.card
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun StartIconCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevated: Boolean = true,
    @DrawableRes iconId: Int,
    iconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
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
    iconTint: Color = FoodDeliveryTheme.colors.onSurfaceVariant,
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

//    Card(
//        modifier = modifier
//            .card()
//            .clickable(
//                enabled = isClickable,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = rememberRipple(),
//                onClick = onClick ?: {}
//            ),
//        colors = FoodDeliveryTheme.colors.cardColors()
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            val iconTint = iconColor ?: FoodDeliveryTheme.colors.onSurfaceVariant
//            Icon(
//                modifier = Modifier.icon24(),
//                imageVector = ImageVector.vectorResource(iconId),
//                contentDescription = stringResource(iconDescriptionStringId),
//                tint = iconTint
//            )
//            val labelText = labelStringId?.let { id ->
//                stringResource(id)
//            } ?: label
//            OverflowingText(
//                modifier = Modifier
//                    .padding(start = FoodDeliveryTheme.dimensions.mediumSpace)
//                    .weight(1f),
//                text = labelText,
//                style = FoodDeliveryTheme.typography.body1,
//                color = FoodDeliveryTheme.colors.onSurface
//            )
//        }
//    }
}

@Preview
@Composable
fun IconCardPreview() {
    StartIconCard(
        iconId = R.drawable.ic_info,
        labelStringId = R.string.title_about_app
    )
}

@Preview
@Composable
fun OriginalColorIconCardPreview() {
    StartIconCard(
        iconId = R.drawable.ic_bb_logo,
        iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        labelStringId = R.string.title_about_app
    )
}
