package com.bunbeauty.papakarlo.navigation.useraddresslist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.update.UpdateRoute
import kotlinx.serialization.Serializable


@Serializable
data object UserAddressListDestination

fun NavGraphBuilder.userAddressListScreenRoute() {
    composable<UserAddressListDestination> {
        UpdateRoute()
    }
}
