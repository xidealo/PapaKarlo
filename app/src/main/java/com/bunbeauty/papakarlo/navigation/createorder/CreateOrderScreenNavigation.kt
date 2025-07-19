package com.bunbeauty.papakarlo.navigation.createorder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object CreateOrderScreenDestination

fun NavGraphBuilder.createOrderScreenRoute(

) {
    composable<CreateOrderScreenDestination> {
        SplashRoute(
            goToUpdateFragment = {},
            goToSelectCityFragment = {},
            goToMenuFragment = {},
        )
    }
}
