package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun NavigationTextCard(
    modifier: Modifier = Modifier,
    @StringRes hintStringId: Int,
    label: String?,
    isClickable: Boolean = true,
    onClick: () -> Unit
) {
    FoodDeliveryCard(
        modifier = modifier,
        enabled = isClickable,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                vertical = FoodDeliveryTheme.dimensions.smallSpace
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                Text(
                    text = stringResource(hintStringId),
                    style = FoodDeliveryTheme.typography.labelSmall.medium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                )
                Text(
                    text = label ?: "",
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
            }
            Icon(
                modifier = Modifier.icon16(),
                imageVector = ImageVector.vectorResource(R.drawable.ic_right_arrow),
                contentDescription = stringResource(R.string.description_ic_next),
                tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TextNavigationCardPreview() {
    FoodDeliveryTheme {
        NavigationTextCard(
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
            hintStringId = R.string.hint_settings_phone,
            label = "+7 999 000-00-00",
            onClick = {}
        )
    }
}
