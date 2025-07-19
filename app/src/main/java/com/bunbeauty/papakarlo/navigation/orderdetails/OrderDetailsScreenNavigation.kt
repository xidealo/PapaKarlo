package com.bunbeauty.papakarlo.navigation.orderdetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderDetailsRoute
import kotlinx.serialization.Serializable


@Serializable
data class OrderDetailsScreenDestination(
    val orderUuid: String,
)

fun NavGraphBuilder.orderDetailsScreenRoute(
    back: () -> Unit,
) {
    composable<OrderDetailsScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<OrderDetailsScreenDestination>()
        OrderDetailsRoute(
            orderUuid = args.orderUuid,
            back = back
        )
    }
}
