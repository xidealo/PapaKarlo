package com.bunbeauty.papakarlo.navigation.cafelist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.cafe.screen.cafelist.CafeListRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object CafeListScreenDestination

fun NavGraphBuilder.cafeListScreenRoute(
    back: () -> Unit,
) {
    composable<CafeListScreenDestination> {
        CafeListRoute(
            back = back,
        )
    }
}
