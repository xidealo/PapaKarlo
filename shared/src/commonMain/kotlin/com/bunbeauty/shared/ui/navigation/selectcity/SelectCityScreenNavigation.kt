package com.bunbeauty.shared.ui.navigation.selectcity

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.city.screen.selectcity.SelectCityRoute
import kotlinx.serialization.Serializable

@Serializable
data object SelectCityScreenDestination

fun NavController.navigateToSelectCityScreen(navOptions: NavOptions) = navigate(route = SelectCityScreenDestination, navOptions)

fun NavGraphBuilder.selectCityScreenRoute(goToMenuFragment: () -> Unit) {
    composable<SelectCityScreenDestination>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                navAnimationSpecDurationForSlide
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForEnterFade
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide
            )
        }
    ) {
        SelectCityRoute(
            goToMenuFragment = goToMenuFragment,
        )
    }
}
