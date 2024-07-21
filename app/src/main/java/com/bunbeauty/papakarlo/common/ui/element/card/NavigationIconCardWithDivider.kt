package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun NavigationIconCardWithDivider(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes iconDescriptionStringId: Int? = null,
    @StringRes labelStringId: Int? = null,
    label: String = "",
    elevated: Boolean = false,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        onClick = onClick,
        elevated = elevated,
        shape = RoundedCornerShape(0.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(iconId),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = iconDescriptionStringId?.let { stringId ->
                        stringResource(stringId)
                    }
                )
                val labelText = labelStringId?.let { id ->
                    stringResource(id)
                } ?: label
                OverflowingText(
                    modifier = Modifier
                        .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                        .weight(1f),
                    text = labelText,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                Icon(
                    modifier = Modifier.icon16(),
                    painter = painterResource(R.drawable.ic_right_arrow),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = stringResource(R.string.description_ic_next)
                )

            }
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = FoodDeliveryTheme.colors.mainColors.stroke
            )
        }

    }
}

@Preview
@Composable
private fun NavigationIconCardPreview() {
    FoodDeliveryTheme {
        NavigationIconCardWithDivider(
            iconId = R.drawable.ic_info,
            iconDescriptionStringId = R.string.description_ic_about,
            label = "Текст"
        ) {}
    }
}
