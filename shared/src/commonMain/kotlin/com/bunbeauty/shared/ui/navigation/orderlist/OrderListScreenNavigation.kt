package com.bunbeauty.shared.ui.navigation.orderlist

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.order.ui.screen.orderlist.OrderListRoute
import kotlinx.serialization.Serializable

@Serializable
data object OrderListScreenDestination

fun NavController.navigateToOrderListScreen(navOptions: NavOptions) =
    navigate(
        route = OrderListScreenDestination,
        navOptions,
    )

fun NavGraphBuilder.orderListScreenRoute(
    back: () -> Unit,
    goToOrderDetails: (String) -> Unit,
) {
    composable<OrderListScreenDestination>(
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
        OrderListRoute(
            back = back,
            goToOrderDetails = goToOrderDetails,
        )
    }
}
