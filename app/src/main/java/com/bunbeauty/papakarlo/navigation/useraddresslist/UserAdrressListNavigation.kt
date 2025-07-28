package com.bunbeauty.papakarlo.navigation.useraddresslist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.address.screen.useraddresslist.UserAddressListRoute
import kotlinx.serialization.Serializable

@Serializable
data object UserAddressListScreenDestination

fun NavController.navigateToUserAddressListScreen(
    navOptions: NavOptions
) = navigate(route = UserAddressListScreenDestination, navOptions)

fun NavGraphBuilder.userAddressListScreenRoute(
    back: () -> Unit,
    goToCreateAddress: () -> Unit
) {
    composable<UserAddressListScreenDestination> {
        UserAddressListRoute(
            back = back,
            goToCreateAddress = goToCreateAddress
        )
    }
}
