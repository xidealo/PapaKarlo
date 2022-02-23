package com.bunbeauty.papakarlo.compose.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.icon
import com.bunbeauty.papakarlo.compose.smallIcon
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationIconCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes iconDescription: Int,
    @StringRes label: Int,
    hasShadow: Boolean = true,
    onClick: () -> Unit
) {
    FoodDeliveryTheme {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
                .apply {
                    if (hasShadow) {
                        shadow(1.dp, mediumRoundedCornerShape)
                    }
                }
                .clip(mediumRoundedCornerShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                ),
            backgroundColor = FoodDeliveryTheme.colors.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.icon(),
                    imageVector = ImageVector.vectorResource(iconId),
                    contentDescription = stringResource(iconDescription),
                    tint = FoodDeliveryTheme.colors.onSurfaceVariant
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                        .weight(1f),
                    text = stringResource(label),
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Icon(
                    modifier = Modifier.smallIcon(),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                    contentDescription = stringResource(R.string.description_ic_next),
                    tint = FoodDeliveryTheme.colors.onSurfaceVariant
                )
            }
        }
    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun NavigationIconCardPreview() {
    NavigationIconCard(
        iconId = R.drawable.ic_info,
        iconDescription = R.string.description_ic_about,
        label = R.string.title_about_app
    ) {}
}
