package com.bunbeauty.shared.presentation.splash

import com.bunbeauty.shared.domain.feature.splash.CheckOneCityUseCase
import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.feature.splash.SaveOneCityUseCase
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel

class SplashViewModel(
    private val checkUpdateUseCase: CheckUpdateUseCase,
    private val cityInteractor: ICityInteractor,
    private val getIsOneCityUseCase: CheckOneCityUseCase,
    private val saveOneCityUseCase: SaveOneCityUseCase
) : SharedStateViewModel<Splash.DataState, Splash.Action, Splash.Event>(
    initDataState = Splash.DataState
) {

    override fun reduce(action: Splash.Action, dataState: Splash.DataState) {
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
                        Splash.Event.NavigateToUpdateEvent
                    }
                }
            },
            onError = {
                // add error state
            }
        )
    }

    private suspend fun checkIsCitySelected() {
        if (cityInteractor.checkIsCitySelected()) {
            addEvent {
                Splash.Event.NavigateToMenuEvent
            }
        } else {
            checkOneCity()
        }
    }

    private suspend fun checkOneCity() {
        if (getIsOneCityUseCase()) {
            saveOneCityUseCase()
            addEvent {
                Splash.Event.NavigateToMenuEvent
            }
        } else {
            addEvent {
                Splash.Event.NavigateToSelectCityEvent
            }
        }
    }
}
