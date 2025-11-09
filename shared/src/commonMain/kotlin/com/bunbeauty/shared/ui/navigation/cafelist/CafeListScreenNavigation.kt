package com.bunbeauty.shared.ui.navigation.cafelist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.screen.cafe.screen.cafelist.CafeListRoute
import kotlinx.serialization.Serializable

@Serializable
data object CafeListScreenDestination

fun NavController.navigateToCafeListScreen(navOptions: NavOptions) =
    navigate(
        route = CafeListScreenDestination,
        navOptions,
    )

fun NavGraphBuilder.cafeListScreenRoute(back: () -> Unit) {
    composable<CafeListScreenDestination> {
        CafeListRoute(
            back = back,
        )
    }
}
