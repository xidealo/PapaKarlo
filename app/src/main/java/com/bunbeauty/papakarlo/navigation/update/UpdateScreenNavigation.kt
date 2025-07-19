package com.bunbeauty.papakarlo.navigation.update

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bunbeauty.papakarlo.feature.update.UpdateRoute
import kotlinx.serialization.Serializable


@Serializable
data object UpdateScreenDestination

fun NavGraphBuilder.updateScreenRoute() {
    composable<UpdateScreenDestination> {
        UpdateRoute()
    }
}
