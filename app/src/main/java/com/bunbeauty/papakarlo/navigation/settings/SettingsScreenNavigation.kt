package com.bunbeauty.papakarlo.navigation.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenDestination

fun NavController.navigateToSettingsScreen(
    navOptions: NavOptions
) = navigate(route = SettingsScreenDestination, navOptions)

fun NavGraphBuilder.settingsScreenRoute(
    back: () -> Unit
) {
    composable<SettingsScreenDestination> {
        SettingsRoute(
            back = back
        )
    }
}
