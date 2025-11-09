package com.bunbeauty.shared.ui.navigation.createorder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.screen.createorder.CreateOrderRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateOrderScreenDestination

fun NavController.navigateToCreateOrderScreen(navOptions: NavOptions) =
    navigate(route = CreateOrderScreenDestination, navOptions)

fun NavGraphBuilder.createOrderScreenRoute(
    back: () -> Unit,
    goToProfile: () -> Unit,
    goToCreateAddress: () -> Unit,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<CreateOrderScreenDestination> {
        CreateOrderRoute(
            back = back,
            goToProfile = goToProfile,
            goToCreateAddress = goToCreateAddress,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
