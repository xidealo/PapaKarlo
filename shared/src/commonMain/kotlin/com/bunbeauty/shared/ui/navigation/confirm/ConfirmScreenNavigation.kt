package com.bunbeauty.shared.ui.navigation.confirm

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.auth.ui.ConfirmRoute
import com.bunbeauty.core.model.SuccessLoginDirection
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmScreenDestination(
    val phoneNumber: String,
    val successLoginDirection: String,
)

fun NavController.navigateToConfirmScreen(
    navOptions: NavOptions,
    phoneNumber: String,
    successLoginDirection: SuccessLoginDirection,
) = navigate(
    route =
        ConfirmScreenDestination(
            phoneNumber = phoneNumber,
            successLoginDirection = successLoginDirection.name,
        ),
    navOptions = navOptions,
)

fun NavGraphBuilder.confirmScreenRoute(
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<ConfirmScreenDestination>(
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
        val args = backStackEntry.toRoute<ConfirmScreenDestination>()

        ConfirmRoute(
            phoneNumber = args.phoneNumber,
            successLoginDirection = SuccessLoginDirection.valueOf(args.successLoginDirection),
            back = back,
            goBackToProfileFragment = goBackToProfileFragment,
            goToCreateOrderFragment = goToCreateOrderFragment,
            showErrorMessage = showErrorMessage,
        )
    }
}
