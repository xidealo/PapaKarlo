package com.bunbeauty.papakarlo.common.ui.element.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.main.NavigationBarOptions

enum class NavigationBarItem {
    CAFE_LIST,
    MENU,
    PROFILE
}

@Composable
fun FoodDeliveryNavigationBar(options: NavigationBarOptions) {
    if (options is NavigationBarOptions.Visible) {
        NavigationBar(
            modifier = Modifier.shadow(FoodDeliveryTheme.dimensions.surfaceElevation),
            containerColor = FoodDeliveryTheme.colors.mainColors.surface
        ) {
            FoodDeliveryBottomItem(
                selected = options.selectedItem == NavigationBarItem.CAFE_LIST,
                iconId = R.drawable.ic_cafes,
                labelId = R.string.title_bottom_navigation_menu_cafe_list,
                onClick = {
                    options.navController.navigateSafe(R.id.global_to_cafeListFragment)
                }
            )
            FoodDeliveryBottomItem(
                selected = options.selectedItem == NavigationBarItem.MENU,
                iconId = R.drawable.ic_menu,
                labelId = R.string.title_bottom_navigation_menu_menu,
                onClick = {
                    options.navController.navigateSafe(R.id.global_to_menuFragment)
                }
            )
            FoodDeliveryBottomItem(
                selected = options.selectedItem == NavigationBarItem.PROFILE,
                iconId = R.drawable.ic_profile,
                labelId = R.string.title_bottom_navigation_profile,
                onClick = {
                    options.navController.navigateSafe(R.id.global_to_profileFragment)
                }
            )
        }
    }
}

@Composable
private fun RowScope.FoodDeliveryBottomItem(
    selected: Boolean,
    @DrawableRes iconId: Int,
    @StringRes labelId: Int,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = {
            if (!selected) {
                onClick()
            }
        },
        label = {
            Text(
                text = stringResource(labelId),
                style = FoodDeliveryTheme.typography.labelMedium.medium
            )
        },
        icon = {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                contentDescription = null
            )
        },
        colors = FoodDeliveryNavigationBarDefaults.navigationBarItemColors()
    )
}
