package com.bunbeauty.shared.ui.navigation.useraddresslist

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.address.screen.useraddresslist.UserAddressListRoute
import kotlinx.serialization.Serializable

@Serializable
data object UserAddressListScreenDestination

fun NavController.navigateToUserAddressListScreen(navOptions: NavOptions) = navigate(route = UserAddressListScreenDestination, navOptions)

fun NavGraphBuilder.userAddressListScreenRoute(
    back: () -> Unit,
    goToCreateAddress: () -> Unit,
) {
    composable<UserAddressListScreenDestination>(
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
        UserAddressListRoute(
            back = back,
            goToCreateAddress = goToCreateAddress,
        )
    }
}
