package com.bunbeauty.papakarlo.navigation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.auth.LoginRoute
import com.bunbeauty.papakarlo.feature.splash.SplashRoute
import com.bunbeauty.papakarlo.navigation.orderdetails.OrderDetailsScreenDestination
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable


@Serializable
data class LoginScreenDestination(
    val successLoginDirection: SuccessLoginDirection,
)

fun NavGraphBuilder.loginScreenRoute(
    back: () -> Unit,
    goToConfirm: (phoneNumber: String, successLoginDirection: SuccessLoginDirection) -> Unit,
) {
    composable<LoginScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<LoginScreenDestination>()

        LoginRoute(
            successLoginDirection = args.successLoginDirection,
            back = back,
            goToConfirm = goToConfirm,
        )
    }
}
