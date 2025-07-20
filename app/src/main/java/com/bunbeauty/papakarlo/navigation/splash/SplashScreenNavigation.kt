package com.bunbeauty.papakarlo.navigation.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object SplashScreenDestination

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
