package com.bunbeauty.papakarlo.navigation.createaddress

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.address.screen.createaddress.CreateAddressRoute
import com.bunbeauty.papakarlo.navigation.cafelist.CafeListScreenDestination
import kotlinx.serialization.Serializable


@Serializable
data object CreateAddressScreenDestination

fun NavController.navigateToCreateAddressScreenDestination(
    navOptions: NavOptions,
) = navigate(
    route = CreateAddressScreenDestination, navOptions
)

fun NavGraphBuilder.createAddressScreenRoute(
    back: () -> Unit,
) {
    composable<CreateAddressScreenDestination> {
        CreateAddressRoute(
            back = back,
        )
    }
}
