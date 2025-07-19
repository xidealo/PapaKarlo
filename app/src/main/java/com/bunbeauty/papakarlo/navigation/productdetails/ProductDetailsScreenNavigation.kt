package com.bunbeauty.papakarlo.navigation.productdetails

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
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
