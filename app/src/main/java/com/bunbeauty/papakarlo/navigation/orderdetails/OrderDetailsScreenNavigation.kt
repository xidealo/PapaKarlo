package com.bunbeauty.papakarlo.navigation.orderdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderDetailsRoute
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
    composable<OrderDetailsScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<OrderDetailsScreenDestination>()
        OrderDetailsRoute(
            orderUuid = args.orderUuid,
            back = back,
        )
    }
}
