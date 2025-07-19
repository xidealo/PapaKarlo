package com.bunbeauty.papakarlo.navigation.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object SettingsScreenDestination

fun NavGraphBuilder.settingsScreenRoute(
    back: () -> Unit,
) {
    composable<SettingsScreenDestination> {
        SettingsRoute(
            back = back
        )
    }
}
