package com.bunbeauty.papakarlo.navigation.consumercart

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.consumercart.ConsumerCartRoute
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.serialization.Serializable

@Serializable
data object ConsumerCartScreenDestination

fun NavController.navigateConsumerCartScreen(navOptions: NavOptions) = navigate(route = ConsumerCartScreenDestination, navOptions)

fun NavGraphBuilder.consumerCartScreenRoute(
    back: () -> Unit,
    goToMenuFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    goToLoginFragment: (SuccessLoginDirection) -> Unit,
    goToProductFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        additionUuidList: List<String>,
        cartProductUuid: String?,
    ) -> Unit,
) {
    composable<ConsumerCartScreenDestination> {
        ConsumerCartRoute(
            back = back,
            goToMenuFragment = goToMenuFragment,
            goToCreateOrderFragment = goToCreateOrderFragment,
            goToLoginFragment = goToLoginFragment,
            goToProductFragment = goToProductFragment,
        )
    }
}
