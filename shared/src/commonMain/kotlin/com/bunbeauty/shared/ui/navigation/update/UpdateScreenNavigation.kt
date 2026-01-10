package com.bunbeauty.shared.ui.navigation.update

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.update.ui.UpdateRoute
import kotlinx.serialization.Serializable

@Serializable
data object UpdateScreenDestination

fun NavController.navigateToUpdateScreen(navOptions: NavOptions) = navigate(route = UpdateScreenDestination, navOptions)

fun NavGraphBuilder.updateScreenRoute() {
    composable<UpdateScreenDestination> {
        UpdateRoute()
    }
}
