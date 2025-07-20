package com.bunbeauty.papakarlo.navigation.confirm

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bunbeauty.papakarlo.feature.auth.ConfirmRoute
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import kotlinx.serialization.Serializable


@Serializable
data class CafeListScreenDestination(
    val phoneNumber: String,
    val successLoginDirection: SuccessLoginDirection,
)

fun NavGraphBuilder.confirmScreenRoute(
    back: () -> Unit,
    goBackToProfileFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
) {
    composable<CafeListScreenDestination> { backStackEntry ->
        val args = backStackEntry.toRoute<CafeListScreenDestination>()

        ConfirmRoute(
            phoneNumber = args.phoneNumber,
            successLoginDirection = args.successLoginDirection,
            back = back,
            goBackToProfileFragment = goBackToProfileFragment,
            goToCreateOrderFragment = goToCreateOrderFragment,
        )
    }
}
