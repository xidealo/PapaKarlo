package com.bunbeauty.papakarlo.navigation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenDestination

fun NavGraphBuilder.profileScreenRoute(
    back: () -> Unit,
    goToUserAddress: (Boolean) -> Unit,
    goToLogin: (SuccessLoginDirection) -> Unit,
    goToOrderDetailsFragment: (String) -> Unit,
    goToOrdersFragment: () -> Unit,
    goToSettingsFragment: () -> Unit,
    goToAboutAppBottomSheet: () -> Unit,
    goToCafeListFragment: () -> Unit,
) {
    composable<ProfileScreenDestination> {
        ProfileRoute(
            back = back,
            goToUserAddress = goToUserAddress,
            goToLogin = goToLogin,
            goToOrderDetailsFragment = goToOrderDetailsFragment,
            goToOrdersFragment = goToOrdersFragment,
            goToSettingsFragment = goToSettingsFragment,
            goToAboutAppBottomSheet = goToAboutAppBottomSheet,
            goToCafeListFragment = goToCafeListFragment,
        )
    }
}
