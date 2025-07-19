package com.bunbeauty.papakarlo.navigation.orderlist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.order.screen.orderlist.OrderListRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object OrderListScreenDestination

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
