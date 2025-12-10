package com.bunbeauty.shared.ui.navigation.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.ui.screen.profile.screen.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenDestination

fun NavController.navigateToSettingsScreen(navOptions: NavOptions) =
    navigate(route = SettingsScreenDestination, navOptions)

fun NavGraphBuilder.settingsScreenRoute(
    back: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<SettingsScreenDestination> {
        SettingsRoute(
            back = back,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
