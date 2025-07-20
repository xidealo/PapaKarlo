package com.bunbeauty.papakarlo.navigation.confirm

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.auth.ConfirmRoute
import com.bunbeauty.papakarlo.navigation.login.LoginScreenDestination
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable


@Serializable
data class ConfirmScreenDestination(
    val phoneNumber: String,
    val successLoginDirection: SuccessLoginDirection,
)

fun NavController.navigateToConfirmScreen(
    navOptions: NavOptions,
    phoneNumber: String,
    successLoginDirection: SuccessLoginDirection,
) = navigate(
    route = ConfirmScreenDestination(
        phoneNumber = phoneNumber,
        successLoginDirection = successLoginDirection,
    ),
    navOptions = navOptions
)

fun NavGraphBuilder.confirmScreenRoute(
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
) {
    composable<ConfirmScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<ConfirmScreenDestination>()

        ConfirmRoute(
            phoneNumber = args.phoneNumber,
            successLoginDirection = args.successLoginDirection,
            back = back,
            goBackToProfileFragment = goBackToProfileFragment,
            goToCreateOrderFragment = goToCreateOrderFragment,
        )
    }
}
