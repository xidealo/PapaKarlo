package com.bunbeauty.papakarlo.navigation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object SplashScreenDestination

fun NavController.navigateToSplashScreen(
    navOptions: NavOptions,
) = navigate(route = SplashScreenDestination, navOptions)

fun NavGraphBuilder.splashScreenRoute(
    goToUpdateFragment: () -> Unit,
    goToSelectCityFragment: () -> Unit,
    goToMenuFragment: () -> Unit,
) {
    composable<SplashScreenDestination> {
        SplashRoute(
            goToUpdateFragment = goToUpdateFragment,
            goToSelectCityFragment = goToSelectCityFragment,
            goToMenuFragment = goToMenuFragment,
        )
    }
}
