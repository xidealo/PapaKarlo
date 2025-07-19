package com.bunbeauty.papakarlo.navigation.consumercart

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object ConsumerCartScreenDestination

fun NavGraphBuilder.consumerCartScreenRoute(

) {
    composable<ConsumerCartScreenDestination> {
        SplashRoute(
            goToUpdateFragment = {},
            goToSelectCityFragment = {},
            goToMenuFragment = {},
        )
    }
}
