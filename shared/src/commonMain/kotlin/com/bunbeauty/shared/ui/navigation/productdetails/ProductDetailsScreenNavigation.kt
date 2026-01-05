package com.bunbeauty.shared.ui.navigation.productdetails

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.navAnimationSpecDurationForPopFade
import com.bunbeauty.shared.ui.navigation.navAnimationSpecScaleForFade
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

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.productDetailsScreenRoute(
    sharedTransitionScope: SharedTransitionScope,
    back: () -> Unit,
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<ProductDetailsScreenDestination>(
        enterTransition = {
            fadeIn(
                navAnimationSpecDurationForEnterFade
            ) + scaleIn(
                initialScale = navAnimationSpecScaleForFade,
                animationSpec = navAnimationSpecDurationForEnterFade
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForEnterFade
            )
        },
        popEnterTransition = {
            fadeIn(
                navAnimationSpecDurationForPopFade
            ) + scaleIn(
                initialScale = navAnimationSpecScaleForFade,
                animationSpec = navAnimationSpecDurationForPopFade
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForPopFade
            )
        }
    ) { backStackEntry ->
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
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
        )
    }
}
