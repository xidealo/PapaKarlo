package com.bunbeauty.splash.presentation

import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.city.ICityInteractor
import com.bunbeauty.core.domain.splash.CheckUpdateUseCase
import com.bunbeauty.core.extension.launchSafe

class SplashViewModel(
    private val checkUpdateUseCase: CheckUpdateUseCase,
    private val cityInteractor: ICityInteractor,
) : SharedStateViewModel<Splash.DataState, Splash.Action, Splash.Effect>(
        initDataState = Splash.DataState,
    ) {
    override fun reduce(
        action: Splash.Action,
        dataState: Splash.DataState,
    ) {
        when (action) {
            is Splash.Action.Init -> checkAppVersion()
        }
    }

    private fun checkAppVersion() {
        sharedScope.launchSafe(
            block = {
                if (checkUpdateUseCase()) {
                    checkIsCitySelected()
                } else {
                    addEvent {
                        Splash.Effect.NavigateToUpdateEffect
                    }
                }
            },
            onError = {
                // add error state
            },
        )
    }

    private suspend fun checkIsCitySelected() {
        if (cityInteractor.checkIsCitySelected()) {
            addEvent {
                Splash.Effect.NavigateToMenuEffect
            }
        } else {
            addEvent {
                Splash.Effect.NavigateToSelectCityEffect
            }
        }
    }
}
