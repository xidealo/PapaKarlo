package com.bunbeauty.papakarlo.navigation.selectcity

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.city.screen.selectcity.SelectCityRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.papakarlo.navigation.menu.MenuScreenDestination
import kotlinx.serialization.Serializable


@Serializable
data object SelectCityScreenDestination

fun NavController.navigateToSelectCityScreen(
    navOptions: NavOptions,
) = navigate(route = SelectCityScreenDestination, navOptions)


fun NavGraphBuilder.selectCityScreenRoute(
    goToMenuFragment: () -> Unit,
    ) {
    composable<SelectCityScreenDestination> {
        SelectCityRoute(
            goToMenuFragment = goToMenuFragment,
        )
    }
}
