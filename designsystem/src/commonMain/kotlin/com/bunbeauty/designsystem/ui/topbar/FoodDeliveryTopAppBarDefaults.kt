package com.bunbeauty.designsystem.ui.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme

object FoodDeliveryTopAppBarDefaults {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun topAppBarColors(
        containerColor: Color = Color.Transparent,
        scrolledContainerColor: Color = Color.Transparent,
        navigationIconContentColor: Color = FoodDeliveryTheme.colors.mainColors.onSurface,
        titleContentColor: Color = FoodDeliveryTheme.colors.mainColors.onSurface,
        actionIconContentColor: Color = FoodDeliveryTheme.colors.mainColors.onSurface,
    ) = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        scrolledContainerColor = scrolledContainerColor,
        navigationIconContentColor = navigationIconContentColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconContentColor,
    )
}
