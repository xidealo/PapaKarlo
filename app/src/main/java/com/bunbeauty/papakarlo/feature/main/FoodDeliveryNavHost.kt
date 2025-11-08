package com.bunbeauty.papakarlo.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bunbeauty.papakarlo.navigation.foodDeliveryNavGraphBuilder
import com.bunbeauty.papakarlo.navigation.splash.SplashScreenDestination

@Composable
fun FoodDeliveryNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination,
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        foodDeliveryNavGraphBuilder(
            navController = navController,
        )
    }
}