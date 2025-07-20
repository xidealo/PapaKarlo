package com.bunbeauty.papakarlo.navigation.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.papakarlo.navigation.selectcity.SelectCityScreenDestination
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenDestination

fun NavController.navigateToProfileScreen(
    navOptions: NavOptions,
) = navigate(route = ProfileScreenDestination, navOptions)

fun NavGraphBuilder.profileScreenRoute(
    back: () -> Unit,
    goToUserAddress: (Boolean) -> Unit,
    goToLogin: (SuccessLoginDirection) -> Unit,
    goToOrderDetailsFragment: (String) -> Unit,
    goToOrdersFragment: () -> Unit,
    goToSettingsFragment: () -> Unit,
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
            goToCafeListFragment = goToCafeListFragment,
        )
    }
}
