package com.bunbeauty.papakarlo.compose.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.icon
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes iconDescriptionStringId: Int,
    iconColor: Color? = null,
    @StringRes labelStringId: Int? = null,
    label: String = "",
) {
    FoodDeliveryTheme {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
                .clip(mediumRoundedCornerShape),
            backgroundColor = FoodDeliveryTheme.colors.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconTint = iconColor ?: FoodDeliveryTheme.colors.onSurfaceVariant
                Icon(
                    modifier = Modifier.icon(),
                    imageVector = ImageVector.vectorResource(iconId),
                    contentDescription = stringResource(iconDescriptionStringId),
                    tint = iconTint
                )
                val labelText = labelStringId?.let { id ->
                    stringResource(id)
                } ?: label
                Text(
                    modifier = Modifier
                        .padding(start = FoodDeliveryTheme.dimensions.mediumSpace)
                        .weight(1f),
                    text = labelText,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onSurface
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun IconCardPreview() {
    IconCard(
        iconId = R.drawable.ic_info,
        iconDescriptionStringId = R.string.description_ic_about,
        labelStringId = R.string.title_about_app
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun OriginalColorIconCardPreview() {
    IconCard(
        iconId = R.drawable.ic_bb_logo,
        iconDescriptionStringId = R.string.description_ic_about,
        iconColor = FoodDeliveryTheme.colors.bunBeautyBrandColor,
        labelStringId = R.string.title_about_app,
    )
}
