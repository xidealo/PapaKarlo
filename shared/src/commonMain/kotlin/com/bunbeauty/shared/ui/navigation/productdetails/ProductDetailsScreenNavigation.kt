package com.bunbeauty.shared.ui.navigation.productdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import com.bunbeauty.shared.ui.screen.productdetails.ProductDetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailsScreenDestination(
    val uuid: String,
    val name: String,
    val productDetailsOpenedFrom: String,
    val additionUuidList: List<String>,
    val cartProductUuid: String?,
)

fun NavController.navigateToProductDetailsScreen(
    navOptions: NavOptions,
    uuid: String,
    name: String,
    productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    additionUuidList: List<String>,
    cartProductUuid: String?,
) = navigate(
    route =
        ProductDetailsScreenDestination(
            uuid = uuid,
            name = name,
            productDetailsOpenedFrom = productDetailsOpenedFrom.name,
            additionUuidList = additionUuidList,
            cartProductUuid = cartProductUuid,
        ),
    navOptions = navOptions,

    )

fun NavGraphBuilder.productDetailsScreenRoute(
    back: () -> Unit,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<ProductDetailsScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<ProductDetailsScreenDestination>()
        ProductDetailsRoute(
            back = back,
            menuProductUuid = args.uuid,
            menuProductName = args.name,
            productDetailsOpenedFrom = ProductDetailsOpenedFrom.valueOf(args.productDetailsOpenedFrom),
            cartProductUuid = args.cartProductUuid,
            additionUuidList = args.additionUuidList,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
