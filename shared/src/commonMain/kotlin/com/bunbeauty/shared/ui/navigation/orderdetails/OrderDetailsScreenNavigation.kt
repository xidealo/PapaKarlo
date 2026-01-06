package com.bunbeauty.shared.ui.navigation.orderdetails

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.order.screen.orderdetails.OrderDetailsRoute
import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class OrderDetailsScreenDestination(
    val orderUuid: String,
)

fun NavController.navigateToOrderDetailsScreen(
    navOptions: NavOptions,
    orderUuid: String,
) = navigate(
    route =
        OrderDetailsScreenDestination(
            orderUuid = orderUuid,
        ),
    navOptions,
)

fun NavGraphBuilder.orderDetailsScreenRoute(back: () -> Unit) {
    composable<OrderDetailsScreenDestination>(
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
        val args = backStackEntry.toRoute<OrderDetailsScreenDestination>()
        OrderDetailsRoute(
            orderUuid = args.orderUuid,
            back = back,
        )
    }
}
