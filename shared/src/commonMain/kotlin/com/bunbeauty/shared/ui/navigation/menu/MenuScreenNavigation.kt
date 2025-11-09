package com.bunbeauty.shared.ui.navigation.menu

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import com.bunbeauty.shared.ui.screen.menu.MenuRoute
import kotlinx.serialization.Serializable

@Serializable
data object MenuScreenDestination

fun NavController.navigateToMenuScreen(navOptions: NavOptions) = navigate(route = MenuScreenDestination, navOptions)

fun NavGraphBuilder.menuScreenRoute(
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<MenuScreenDestination> {
        MenuRoute(
            goToProductDetailsFragment = goToProductDetailsFragment,
            goToProfile = goToProfile,
            goToConsumerCart = goToConsumerCart,
            showErrorMessage = showErrorMessage,
        )
    }
}
