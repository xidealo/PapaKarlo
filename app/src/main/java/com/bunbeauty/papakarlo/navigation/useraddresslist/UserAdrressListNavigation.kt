package com.bunbeauty.papakarlo.navigation.useraddresslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.address.screen.useraddresslist.UserAddressListRoute
import com.bunbeauty.papakarlo.feature.update.UpdateRoute
import kotlinx.serialization.Serializable


@Serializable
data object UserAddressListDestination

fun NavGraphBuilder.userAddressListScreenRoute(
    back: () -> Unit,
    goToCreateAddress: () -> Unit,
) {
    composable<UserAddressListDestination> {
        UserAddressListRoute(
            back = back,
            goToCreateAddress = goToCreateAddress
        )
    }
}
