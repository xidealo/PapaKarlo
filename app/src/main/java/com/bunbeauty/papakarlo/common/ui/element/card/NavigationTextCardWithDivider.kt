package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.zeroCardShape
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun NavigationTextCardWithDivider(
    modifier: Modifier = Modifier,
    @StringRes hintStringId: Int,
    label: String?,
    clickable: Boolean = true,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        onClick = onClick,
        elevated = false,
        shape = zeroCardShape
    ) {
        Column {
            Row(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(hintStringId),
                        style = FoodDeliveryTheme.typography.labelSmall,
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = label ?: "",
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }
                Icon(
                    modifier = Modifier.icon16(),
                    painter = painterResource(R.drawable.ic_right_arrow),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = stringResource(R.string.description_ic_next)
                )
            }
            FoodDeliveryHorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

    }
}

@Preview
@Composable
private fun NavigationTextCardWithDividerPreview() {
    FoodDeliveryTheme {
        NavigationTextCardWithDivider(
            hintStringId = R.string.hint_settings_phone,
            label = "улица Доставочная, д 1, кв 1",
            onClick = {}
        )
    }
}
