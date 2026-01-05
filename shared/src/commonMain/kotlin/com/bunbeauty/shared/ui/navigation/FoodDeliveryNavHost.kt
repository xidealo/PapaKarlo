package com.bunbeauty.shared.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bunbeauty.shared.ui.navigation.splash.SplashScreenDestination

val navAnimationSpecDurationForSlide = tween<IntOffset>(durationMillis = 300)
val navAnimationSpecDurationForEnterFade: FiniteAnimationSpec<Float> = tween(
    durationMillis = 220,
    easing = FastOutSlowInEasing
)

val navAnimationSpecDurationForPopFade: FiniteAnimationSpec<Float> = tween(
    durationMillis = 180,
    easing = LinearOutSlowInEasing
)

const val navAnimationSpecScaleForFade = 0.95f

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodDeliveryNavHost(
    showInfoMessage: (String, Int) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = SplashScreenDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            foodDeliveryNavGraphBuilder(
                navController = navController,
                showErrorMessage = showErrorMessage,
                showInfoMessage = showInfoMessage,
                sharedTransitionScope = this@SharedTransitionLayout,
            )
        }
    }
}
