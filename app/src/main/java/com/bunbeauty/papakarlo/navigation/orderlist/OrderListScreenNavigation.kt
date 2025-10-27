package com.bunbeauty.papakarlo.navigation.orderlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.order.screen.orderlist.OrderListRoute
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
    composable<OrderListScreenDestination> {
        OrderListRoute(
            back = back,
            goToOrderDetails = goToOrderDetails,
        )
    }
}
