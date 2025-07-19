package com.bunbeauty.papakarlo.navigation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object LoginScreenDestination

fun NavGraphBuilder.loginScreenRoute(

) {
    composable<LoginScreenDestination> {
        SplashRoute(
            goToUpdateFragment = {},
            goToSelectCityFragment = {},
            goToMenuFragment = {},
        )
    }
}
