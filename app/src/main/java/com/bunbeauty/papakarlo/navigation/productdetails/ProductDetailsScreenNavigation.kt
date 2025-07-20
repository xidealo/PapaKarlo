package com.bunbeauty.papakarlo.navigation.productdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.papakarlo.navigation.consumercart.ConsumerCartScreenDestination
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.serialization.Serializable


@Serializable
data class ProductDetailsScreenDestination(
    val uuid: String,
    val name: String,
    val productDetailsOpenedFrom: ProductDetailsOpenedFrom,
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
    route = ProductDetailsScreenDestination(
        uuid = uuid,
        name = name,
        productDetailsOpenedFrom = productDetailsOpenedFrom,
        additionUuidList = additionUuidList,
        cartProductUuid = cartProductUuid,
    ),
    navOptions = navOptions
)


fun NavGraphBuilder.productDetailsScreenRoute(
    back: () -> Unit,
) {
    composable<ProductDetailsScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<ProductDetailsScreenDestination>()
        ProductDetailsRoute(
            back = back,
            menuProductUuid = args.uuid,
            menuProductName = args.name,
            productDetailsOpenedFrom = args.productDetailsOpenedFrom,
            cartProductUuid = args.cartProductUuid,
            additionUuidList = args.additionUuidList
        )
    }
}
