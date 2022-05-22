package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.card
import com.bunbeauty.papakarlo.common.ui.smallIcon
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun NavigationTextCard(
    modifier: Modifier = Modifier,
    @StringRes hintStringId: Int,
    label: String,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .card(true)
            .clickable(
                enabled = isClickable,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                Text(
                    text = stringResource(hintStringId),
                    style = FoodDeliveryTheme.typography.body2,
                    color = FoodDeliveryTheme.colors.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.verySmallSpace),
                    text = label,
                    style = FoodDeliveryTheme.typography.body1,
                    color = FoodDeliveryTheme.colors.onSurface
                )
            }
            Icon(
                modifier = Modifier.smallIcon(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = stringResource(R.string.description_ic_next),
                tint = FoodDeliveryTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun TextNavigationCardPreview() {
    NavigationTextCard(
        hintStringId = R.string.hint_settings_phone,
        label = "+7 999 000-00-00"
    ) {}
}
