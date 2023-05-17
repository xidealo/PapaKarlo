package com.bunbeauty.papakarlo.common

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafe(directions: NavDirections) {
    val action = currentDestination?.getAction(directions.actionId)
    if (action != null) {
        navigate(directions)
    }
}
