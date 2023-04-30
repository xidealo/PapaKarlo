package com.bunbeauty.papakarlo.common.ui.element.bottom_bar

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryNavigationBarDefaults {

    @Composable
    fun navigationBarItemColors(): NavigationBarItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = FoodDeliveryTheme.colors.mainColors.primary,
        selectedTextColor = FoodDeliveryTheme.colors.mainColors.primary,
        indicatorColor = FoodDeliveryTheme.colors.mainColors.surface,
        unselectedIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
        unselectedTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    )
}
