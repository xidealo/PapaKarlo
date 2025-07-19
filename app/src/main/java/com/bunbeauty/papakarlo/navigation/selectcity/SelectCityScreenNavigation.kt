package com.bunbeauty.papakarlo.navigation.selectcity

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.city.screen.selectcity.SelectCityRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object SelectCityScreenDestination

fun NavGraphBuilder.selectCityScreenRoute() {
    composable<SelectCityScreenDestination> {
        SelectCityRoute(
            goToMenuFragment = {},
        )
    }
}
