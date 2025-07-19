package com.bunbeauty.papakarlo.navigation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object ProfileScreenDestination

fun NavGraphBuilder.profileScreenRoute(

) {
    composable<ProfileScreenDestination> {
        SplashRoute(
            goToUpdateFragment = {},
            goToSelectCityFragment = {},
            goToMenuFragment = {},
        )
    }
}
