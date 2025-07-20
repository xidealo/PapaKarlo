package com.bunbeauty.papakarlo.navigation.createorder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.createorder.CreateOrderRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import kotlinx.serialization.Serializable


@Serializable
data object CreateOrderScreenDestination

fun NavGraphBuilder.createOrderScreenRoute(
    back: () -> Unit,
    goToProfile: () -> Unit,
    goToCreateAddress: () -> Unit,
) {
    composable<CreateOrderScreenDestination> {
        CreateOrderRoute(
            back = back,
            goToProfile = goToProfile,
            goToCreateAddress = goToCreateAddress,
        )
    }
}
