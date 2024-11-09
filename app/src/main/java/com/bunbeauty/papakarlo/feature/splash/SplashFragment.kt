package com.bunbeauty.papakarlo.feature.splash

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toMenuFragment
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toSelectCityFragment
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.toUpdateFragment
import com.bunbeauty.shared.presentation.splash.Splash
import com.bunbeauty.shared.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseComposeFragment<Splash.DataState, SplashViewState, Splash.Action, Splash.Event>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onAction(Splash.Action.Init(version = BuildConfig.VERSION_CODE))
    }

    @Composable
    override fun Splash.DataState.mapState(): SplashViewState {
        return SplashViewState
    }

    @Composable
    override fun Screen(viewState: SplashViewState, onAction: (Splash.Action) -> Unit) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_medium),
                contentDescription = stringResource(R.string.description_company_logo)
            )
        }
    }

    override fun handleEvent(event: Splash.Event) {
        when (event) {
            Splash.Event.NavigateToMenuEvent -> {
                findNavController().navigateSafe(toMenuFragment())
            }

            Splash.Event.NavigateToSelectCityEvent -> {
                findNavController().navigateSafe(toSelectCityFragment())
            }

            Splash.Event.NavigateToUpdateEvent -> {
                findNavController().navigateSafe(toUpdateFragment())
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun SplashPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = SplashViewState,
                onAction = {}
            )
        }
    }
}
