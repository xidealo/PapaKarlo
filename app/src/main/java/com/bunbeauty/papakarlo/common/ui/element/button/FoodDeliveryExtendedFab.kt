package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium

@Composable
fun FoodDeliveryExtendedFab(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    iconBadge: String? = null
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        containerColor = FoodDeliveryTheme.colors.mainColors.primary,
        text = {
            Text(
                text = text,
                style = FoodDeliveryTheme.typography.labelLarge,
                color = FoodDeliveryTheme.colors.mainColors.onPrimary
            )
        },
        icon = {
            icon?.let {
                Box {
                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .icon24(),
                        painter = painterResource(id = icon),
                        tint = FoodDeliveryTheme.colors.mainColors.onPrimary,
                        contentDescription = null
                    )
                    iconBadge?.let {
                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd),
                            containerColor = FoodDeliveryTheme.colors.mainColors.onPrimary,
                            contentColor = FoodDeliveryTheme.colors.mainColors.primary
                        ) {
                            Text(
                                style = FoodDeliveryTheme.typography.labelSmall.medium,
                                text = iconBadge
                            )
                        }
                    }
                }
            }
        },
        onClick = onClick
    )
}

@Preview
@Composable
private fun FoodDeliveryExtendedFabPreview() {
    FoodDeliveryTheme {
        FoodDeliveryExtendedFab(
            text = "SomeText",
            icon = R.drawable.ic_cart_24,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun FoodDeliveryExtendedFabWithBadgePreview() {
    FoodDeliveryTheme {
        FoodDeliveryExtendedFab(
            text = "SomeText",
            icon = R.drawable.ic_cart_24,
            iconBadge = "2",
            onClick = {}
        )
    }
}
