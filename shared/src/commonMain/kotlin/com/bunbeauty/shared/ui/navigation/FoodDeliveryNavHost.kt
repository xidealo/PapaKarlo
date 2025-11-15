package com.bunbeauty.shared.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bunbeauty.shared.ui.navigation.splash.SplashScreenDestination

@Composable
fun FoodDeliveryNavHost(
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination,
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        foodDeliveryNavGraphBuilder(
            navController = navController,
            showErrorMessage = showErrorMessage,
            showInfoMessage = showInfoMessage
        )
    }
}