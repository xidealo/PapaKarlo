package com.bunbeauty.shared.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.logoMedium
import com.bunbeauty.shared.presentation.splash.Splash
import com.bunbeauty.shared.presentation.splash.SplashViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.description_company_logo

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = koinViewModel(),
    goToUpdateFragment: () -> Unit,
    goToSelectCityFragment: () -> Unit,
    goToMenuFragment: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(Splash.Action.Init)
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()
    val onAction =
        remember {
            { event: Splash.Action ->
                viewModel.onAction(event)
            }
        }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    SplashEffect(
        effects = effects,
        goToUpdateFragment = goToUpdateFragment,
        goToSelectCityFragment = goToSelectCityFragment,
        goToMenuFragment = goToMenuFragment,
        consumeEffects = consumeEffects,
    )
    SplashScreen(viewState = viewState, onAction = onAction)
}

@Composable
private fun SplashScreen(
    viewState: Splash.DataState,
    onAction: (Splash.Action) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        logoMedium?.let { logo ->
            Image(
                painter = painterResource(resource = logo),
                contentDescription =
                    stringResource(
                        resource = Res.string.description_company_logo,
                    ),
            )
        }
    }
}

@Composable
private fun SplashEffect(
    effects: List<Splash.Effect>,
    goToUpdateFragment: () -> Unit,
    goToSelectCityFragment: () -> Unit,
    goToMenuFragment: () -> Unit,
    consumeEffects: () -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                Splash.Effect.NavigateToMenuEffect -> goToMenuFragment()

                Splash.Effect.NavigateToSelectCityEffect -> goToSelectCityFragment()

                Splash.Effect.NavigateToUpdateEffect -> goToUpdateFragment()
            }
        }
        consumeEffects()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    FoodDeliveryTheme {
        SplashScreen(
            viewState = Splash.DataState,
            onAction = {},
        )
    }
}
