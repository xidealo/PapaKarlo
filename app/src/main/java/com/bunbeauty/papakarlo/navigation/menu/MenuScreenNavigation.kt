package com.bunbeauty.papakarlo.navigation.menu

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.menu.MenuRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.serialization.Serializable


@Serializable
data object MenuScreenDestination

fun NavController.navigateToMenuScreen(
    navOptions: NavOptions,
) = navigate(route = MenuScreenDestination, navOptions)


fun NavGraphBuilder.menuScreenRoute(
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
) {
    composable<MenuScreenDestination> {
        MenuRoute(
            goToProductDetailsFragment = goToProductDetailsFragment,
            goToProfile = goToProfile,
            goToConsumerCart = goToConsumerCart,
        )
    }
}
