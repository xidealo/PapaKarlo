package com.bunbeauty.papakarlo.common.ui.toolbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryTopAppBarDefaults {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun topAppBarColors(
        containerColor: Color = FoodDeliveryTheme.colors.surface,
        scrolledContainerColor: Color = FoodDeliveryTheme.colors.surface,
        navigationIconContentColor: Color = FoodDeliveryTheme.colors.onSurface,
        titleContentColor: Color = FoodDeliveryTheme.colors.onSurface,
        actionIconContentColor: Color = FoodDeliveryTheme.colors.onSurface,
    ) = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        scrolledContainerColor = scrolledContainerColor,
        navigationIconContentColor = navigationIconContentColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconContentColor
    )
}