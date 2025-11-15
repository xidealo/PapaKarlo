package com.bunbeauty.shared.ui.navigation.confirm

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.ui.screen.auth.ConfirmRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind

@Serializable
data class ConfirmScreenDestination(
    val phoneNumber: String,
    val successLoginDirection: String,
)

fun NavController.navigateToConfirmScreen(
    navOptions: NavOptions,
    phoneNumber: String,
    successLoginDirection: SuccessLoginDirection,
) = navigate(
    route =
        ConfirmScreenDestination(
            phoneNumber = phoneNumber,
            successLoginDirection = successLoginDirection.name,
        ),
    navOptions = navOptions,
)

fun NavGraphBuilder.confirmScreenRoute(
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    composable<ConfirmScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<ConfirmScreenDestination>()

        ConfirmRoute(
            phoneNumber = args.phoneNumber,
            successLoginDirection = SuccessLoginDirection.valueOf(args.successLoginDirection),
            back = back,
            goBackToProfileFragment = goBackToProfileFragment,
            goToCreateOrderFragment = goToCreateOrderFragment,
            showInfoMessage = showInfoMessage,
            showErrorMessage = showErrorMessage,
        )
    }
}
