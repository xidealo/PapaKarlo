package com.bunbeauty.designsystem.ui.element.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.LocalBottomBarPadding
import com.bunbeauty.designsystem.ui.icon24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.ic_cart_24


@Composable
fun FoodDeliveryExtendedFab(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    iconBadge: String? = null,
) {

    ExtendedFloatingActionButton(
        modifier = modifier
            .padding(bottom = LocalBottomBarPadding.current),
        containerColor = FoodDeliveryTheme.colors.mainColors.primary,
        text = {
            Text(
                text = text,
                style = FoodDeliveryTheme.typography.labelLarge,
                color = FoodDeliveryTheme.colors.mainColors.onPrimary,
            )
        },
        icon = {
            icon?.let {
                Box {
                    Icon(
                        modifier =
                            Modifier
                                .padding(4.dp)
                                .icon24(),
                        painter = painterResource(resource = icon),
                        tint = FoodDeliveryTheme.colors.mainColors.onPrimary,
                        contentDescription = null,
                    )
                    iconBadge?.let {
                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd),
                            containerColor = FoodDeliveryTheme.colors.mainColors.onPrimary,
                            contentColor = FoodDeliveryTheme.colors.mainColors.primary,
                        ) {
                            Text(
                                style = FoodDeliveryTheme.typography.labelSmall.medium,
                                text = iconBadge,
                            )
                        }
                    }
                }
            }
        },
        onClick = onClick,
    )
}

@Preview
@Composable
private fun FoodDeliveryExtendedFabPreview() {
    FoodDeliveryTheme {
        FoodDeliveryExtendedFab(
            text = "SomeText",
            icon = Res.drawable.ic_cart_24,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun FoodDeliveryExtendedFabWithBadgePreview() {
    FoodDeliveryTheme {
        FoodDeliveryExtendedFab(
            text = "SomeText",
            icon = Res.drawable.ic_cart_24,
            iconBadge = "2",
            onClick = {},
        )
    }
}
