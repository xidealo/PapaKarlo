package com.bunbeauty.shared.ui.navigation.menu

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.menu.ui.MenuRoute
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.NAV_ANIMATION_SPEC_SCALE_FOR_FADE
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForPopFade
import kotlinx.serialization.Serializable

@Serializable
data object MenuScreenDestination

fun NavController.navigateToMenuScreen(navOptions: NavOptions) =
    navigate(route = MenuScreenDestination, navOptions)

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.menuScreenRoute(
    sharedTransitionScope: SharedTransitionScope,
    goToProductDetailsFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) -> Unit,
    goToProfile: () -> Unit,
    goToConsumerCart: () -> Unit,
    showErrorMessage: (String) -> Unit,
    showInfoMessage: (String, Int) -> Unit,
) {
    composable<MenuScreenDestination>(
        enterTransition = {
            fadeIn(
                navAnimationSpecDurationForEnterFade,
            ) +
                    scaleIn(
                        initialScale = NAV_ANIMATION_SPEC_SCALE_FOR_FADE,
                        animationSpec = navAnimationSpecDurationForEnterFade,
                    )
        },
        exitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForEnterFade,
            )
        },
        popEnterTransition = {
            fadeIn(
                navAnimationSpecDurationForPopFade,
            ) +
                    scaleIn(
                        initialScale = NAV_ANIMATION_SPEC_SCALE_FOR_FADE,
                        animationSpec = navAnimationSpecDurationForPopFade,
                    )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForPopFade,
            )
        },
    ) {
        MenuRoute(
            goToProductDetailsFragment = goToProductDetailsFragment,
            goToProfile = goToProfile,
            goToConsumerCart = goToConsumerCart,
            showErrorMessage = showErrorMessage,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            showInfoMessage = showInfoMessage
        )
    }
}
