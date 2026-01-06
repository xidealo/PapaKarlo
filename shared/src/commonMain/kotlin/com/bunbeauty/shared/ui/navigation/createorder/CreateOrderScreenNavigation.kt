package com.bunbeauty.shared.ui.navigation.createorder

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.createorder.CreateOrderRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateOrderScreenDestination

fun NavController.navigateToCreateOrderScreen(navOptions: NavOptions) = navigate(route = CreateOrderScreenDestination, navOptions)

fun NavGraphBuilder.createOrderScreenRoute(
    back: () -> Unit,
    goToProfile: () -> Unit,
    goToCreateAddress: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<CreateOrderScreenDestination>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                navAnimationSpecDurationForSlide,
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForEnterFade,
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide,
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide,
            )
        },
    ) {
        CreateOrderRoute(
            back = back,
            goToProfile = goToProfile,
            goToCreateAddress = goToCreateAddress,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
