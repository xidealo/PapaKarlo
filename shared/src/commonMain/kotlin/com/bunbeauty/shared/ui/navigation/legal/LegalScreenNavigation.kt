package com.bunbeauty.shared.ui.navigation.legal

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForEnterFade
import com.bunbeauty.shared.ui.navigation.NavAnimationSpec.navAnimationSpecDurationForSlide
import com.bunbeauty.shared.ui.screen.legal.LegalDocumentScreen
import com.bunbeauty.shared.ui.screen.legal.privacyPolicyText
import com.bunbeauty.shared.ui.screen.legal.userAgreementText
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.msg_legal_document_placeholder
import papakarlo.designsystem.generated.resources.title_privacy_policy
import papakarlo.designsystem.generated.resources.title_terms_of_service
import papakarlo.designsystem.generated.resources.title_user_agreement

@Serializable
data object UserAgreementScreenDestination

@Serializable
data object PrivacyPolicyScreenDestination

@Serializable
data object TermsOfServiceScreenDestination

fun NavController.navigateToUserAgreementScreen(navOptions: NavOptions) = navigate(route = UserAgreementScreenDestination, navOptions)

fun NavController.navigateToPrivacyPolicyScreen(navOptions: NavOptions) = navigate(route = PrivacyPolicyScreenDestination, navOptions)

fun NavController.navigateToTermsOfServiceScreen(navOptions: NavOptions) = navigate(route = TermsOfServiceScreenDestination, navOptions)

fun NavGraphBuilder.userAgreementScreenRoute(back: () -> Unit) {
    legalComposable<UserAgreementScreenDestination> {
        val flavor = koinInject<String>(qualifier = flavorQualifier)
        val text = remember(flavor) { userAgreementText(flavor = flavor) }

        LegalDocumentScreen(
            title = stringResource(Res.string.title_user_agreement),
            text = text,
            back = back,
        )
    }
}

fun NavGraphBuilder.privacyPolicyScreenRoute(back: () -> Unit) {
    legalComposable<PrivacyPolicyScreenDestination> {
        val flavor = koinInject<String>(qualifier = flavorQualifier)
        val text = remember(flavor) { privacyPolicyText(flavor = flavor) }

        LegalDocumentScreen(
            title = stringResource(Res.string.title_privacy_policy),
            text = text,
            back = back,
        )
    }
}

fun NavGraphBuilder.termsOfServiceScreenRoute(back: () -> Unit) {
    legalComposable<TermsOfServiceScreenDestination> {
        LegalDocumentScreen(
            title = stringResource(Res.string.title_terms_of_service),
            text = stringResource(Res.string.msg_legal_document_placeholder),
            back = back,
        )
    }
}

// All three legal screens are static and share the same slide-in transitions
// used by the rest of the secondary screens (see SettingsScreenNavigation).
private inline fun <reified T : Any> NavGraphBuilder.legalComposable(noinline content: @Composable () -> Unit) {
    composable<T>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                navAnimationSpecDurationForSlide,
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = navAnimationSpecDurationForEnterFade,
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide,
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                navAnimationSpecDurationForSlide,
            )
        },
    ) {
        content()
    }
}
