package com.bunbeauty.shared.ui.navigation.createaddress

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.screen.address.screen.createaddress.CreateAddressRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateAddressScreenDestination

fun NavController.navigateToCreateAddressScreenDestination(navOptions: NavOptions) =
    navigate(
        route = CreateAddressScreenDestination,
        navOptions,
    )

fun NavGraphBuilder.createAddressScreenRoute(
    back: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<CreateAddressScreenDestination> {
        CreateAddressRoute(
            back = back,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
