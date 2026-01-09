package com.bunbeauty.shared.ui.navigation.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.core.model.SuccessLoginDirection
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.auth.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data class LoginScreenDestination(
    val successLoginDirection: String,
)

fun NavController.navigateToLoginScreen(
    navOptions: NavOptions,
    successLoginDirection: SuccessLoginDirection,
) = navigate(
    route =
        LoginScreenDestination(
            successLoginDirection = successLoginDirection.name,
        ),
    navOptions = navOptions,
)

fun NavGraphBuilder.loginScreenRoute(
    back: () -> Unit,
    goToConfirm: (phoneNumber: String, successLoginDirection: SuccessLoginDirection) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<LoginScreenDestination>(
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
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<LoginScreenDestination>()

        LoginRoute(
            successLoginDirection = SuccessLoginDirection.valueOf(args.successLoginDirection),
            back = back,
            goToConfirm = goToConfirm,
            showErrorMessage = showErrorMessage,
        )
    }
}
