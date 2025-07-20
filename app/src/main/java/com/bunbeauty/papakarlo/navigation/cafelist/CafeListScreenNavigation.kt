package com.bunbeauty.papakarlo.navigation.cafelist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.cafe.screen.cafelist.CafeListRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.papakarlo.navigation.confirm.ConfirmScreenDestination
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable


@Serializable
data object CafeListScreenDestination

fun NavController.navigateToCafeListScreen(
    navOptions: NavOptions,
) = navigate(
    route = CafeListScreenDestination, navOptions
)

fun NavGraphBuilder.cafeListScreenRoute(
    back: () -> Unit,
) {
    composable<CafeListScreenDestination> {
        CafeListRoute(
            back = back,
        )
    }
}
