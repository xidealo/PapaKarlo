package com.bunbeauty.shared.ui.navigation.profile

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.profile.screen.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenDestination

fun NavController.navigateToProfileScreen(navOptions: NavOptions) =
    navigate(route = ProfileScreenDestination, navOptions)

fun NavGraphBuilder.profileScreenRoute(
    back: () -> Unit,
    goToUserAddress: (Boolean) -> Unit,
    goToLogin: (SuccessLoginDirection) -> Unit,
    goToOrderDetailsFragment: (String) -> Unit,
    goToOrdersFragment: () -> Unit,
    goToSettingsFragment: () -> Unit,
    goToCafeListFragment: () -> Unit,
) {
    composable<ProfileScreenDestination>(
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
        ProfileRoute(
            back = back,
            goToUserAddress = goToUserAddress,
            goToLogin = goToLogin,
            goToOrderDetailsFragment = goToOrderDetailsFragment,
            goToOrdersFragment = goToOrdersFragment,
            goToSettingsFragment = goToSettingsFragment,
            goToCafeListFragment = goToCafeListFragment,
        )
    }
}
