package com.bunbeauty.papakarlo.navigation.confirm

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object CafeListScreenDestination

fun NavGraphBuilder.confirmScreenRoute(

) {
    composable<CafeListScreenDestination> {
        SplashRoute(
            goToUpdateFragment = {},
            goToSelectCityFragment = {},
            goToMenuFragment = {},
        )
    }
}
