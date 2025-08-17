package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.LogoutSettingsClickEvent
import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val observeSelectedCityUseCase: ObserveSelectedCityUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase,
    private val disableUserUseCase: DisableUserUseCase,
    private val userInteractor: IUserInteractor,
    private val analyticService: AnalyticService
) : SharedStateViewModel<SettingsState.DataState, SettingsState.Action, SettingsState.Event>(
    SettingsState.DataState()
) {

    private var observeSettingsJob: Job? = null

    override fun reduce(action: SettingsState.Action, dataState: SettingsState.DataState) {
        when (action) {
            SettingsState.Action.BackClick -> backClick()
            SettingsState.Action.LoadData -> loadData()
            SettingsState.Action.OnCityClicked -> onCityClicked()

            SettingsState.Action.OnLogoutClicked -> onLogoutClicked(
                phoneNumber = dataState.settings?.phoneNumber.toString()
            )

            SettingsState.Action.OnLogoutConfirmClicked -> logout()

            SettingsState.Action.CloseLogoutBottomSheet -> onCloseLogoutClicked()
            is SettingsState.Action.OnCitySelected -> onCitySelected(action.cityUuid)
            SettingsState.Action.CloseCityListBottomSheet -> onCloseCityListBottomSheetClicked()
        }
    }

    fun loadData() {
        observeSettings()
        loadCityList()
    }

    private fun backClick() {
        addEvent {
            SettingsState.Event.Back
        }
    }

    private fun onLogoutClicked(phoneNumber: String) {
        analyticService.sendEvent(
            event = LogoutSettingsClickEvent(
                phone = phoneNumber
            )
        )

        setState {
            copy(
                isShowLogoutBottomSheet = true
            )
        }
    }

    private fun onCloseLogoutClicked() {
        setState {
            copy(
                isShowLogoutBottomSheet = false
            )
        }
    }

    private fun onCloseCityListBottomSheetClicked() {
        setState {
            copy(
                isShowCityListBottomSheet = false
            )
        }
    }

    private fun onCityClicked() {
        setState {
            copy(
                isShowCityListBottomSheet = true
            )
        }
    }

    fun onCitySelected(cityUuid: String) {
        sharedScope.launch {
            saveSelectedCityUseCase(cityUuid)
            onCloseCityListBottomSheetClicked()
        }
    }

    fun logout() {
        sharedScope.launchSafe(
            block = {
                setState {
                    copy(
                        isShowLogoutBottomSheet = false
                    )
                }
                observeSettingsJob?.cancel()
                userInteractor.clearUserCache()
                addEvent {
                    SettingsState.Event.Back
                }
            },
            onError = { error ->
                println(error)
            }
        )
    }

    fun disableUser() {
        sharedScope.launch {
            disableUserUseCase()
            logout()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSettings() {
        observeSettingsJob?.cancel()
        observeSettingsJob = observeSelectedCityUseCase().flatMapLatest { city ->
            observeSettingsUseCase().map { settings ->
                setState {
                    val state = if (city == null || settings == null) {
                        SettingsState.DataState.State.ERROR
                    } else {
                        SettingsState.DataState.State.SUCCESS
                    }

                    copy(
                        settings = settings,
                        selectedCity = city,
                        state = state
                    )
                }
            }
        }.launchIn(sharedScope)
    }

    private fun loadCityList() {
        sharedScope.launchSafe(
            block = {
                setState {
                    copy(cityList = getCityListUseCase())
                }
            },
            onError = { error ->
                println(error)
            }
        )
    }
}
