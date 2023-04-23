package com.bunbeauty.papakarlo.feature.main

import androidx.navigation.NavController
import com.bunbeauty.papakarlo.common.ui.element.bottom_bar.NavigationBarItem

data class MainState(
    val connectionLost: Boolean = false,
    val navigationBarOptions: NavigationBarOptions = NavigationBarOptions.Hidden
)

sealed interface NavigationBarOptions {
    object Hidden : NavigationBarOptions
    data class Visible(
        val selectedItem: NavigationBarItem,
        val navController: NavController
    ) : NavigationBarOptions
}
