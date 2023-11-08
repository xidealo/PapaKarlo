package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.LogoutSettingsClickEvent
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.DisableUserUseCase
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val observeSelectedCityUseCase: ObserveSelectedCityUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val getCityListUseCase: GetCityListUseCase,
    private val saveSelectedCityUseCase: SaveSelectedCityUseCase,
    private val disableUserUseCase: DisableUserUseCase,
    private val userInteractor: IUserInteractor,
    private val analyticService: AnalyticService,
) : SharedViewModel() {

    private val mutableSettingsState = MutableStateFlow(SettingsState())
    val settingsState = mutableSettingsState.asCommonStateFlow()

    var observeSettingsJob: Job? = null

    fun loadData() {
        observeSettings()
        loadCityList()
    }

    fun onEmailClicked() {
        mutableSettingsState.update { settingsState ->
            settingsState + SettingsState.Event.ShowEditEmailEvent(settingsState.settings?.email)
        }
    }

    fun onLogoutClicked() {
        analyticService.sendEvent(
            event = LogoutSettingsClickEvent(
                phone = settingsState.value.settings?.phoneNumber.toString()
            ),
        )
        mutableSettingsState.update { settingsState ->
            settingsState + SettingsState.Event.ShowLogoutEvent
        }
    }

    fun onEmailChanged(email: String) {
        sharedScope.launch {
            val isSuccess = updateEmailUseCase(email)
            mutableSettingsState.update { settingsState ->
                settingsState + if (isSuccess) {
                    SettingsState.Event.ShowEmailChangedSuccessfullyEvent
                } else {
                    SettingsState.Event.ShowEmailChangingFailedEvent
                }
            }
        }
    }

    fun onCityClicked() {
        mutableSettingsState.update { settingsState ->
            settingsState + SettingsState.Event.ShowCityListEvent(
                cityList = settingsState.cityList,
                selectedCityUuid = settingsState.selectedCity?.uuid
            )
        }
    }

    fun onCitySelected(cityUuid: String) {
        sharedScope.launch {
            saveSelectedCityUseCase(cityUuid)
        }
    }

    fun consumeEventList(eventList: List<SettingsState.Event>) {
        mutableSettingsState.update { settingsState ->
            settingsState - eventList
        }
    }

    fun logout() {
        sharedScope.launch {
            observeSettingsJob?.cancel()
            userInteractor.clearUserCache()
            mutableSettingsState.update { settingsState ->
                settingsState + SettingsState.Event.Back
            }
        }
    }

    fun disableUser() {
        sharedScope.launch {
            disableUserUseCase()
            logout()
        }
    }

    private fun observeSettings() {
        observeSettingsJob?.cancel()
        observeSettingsJob = observeSelectedCityUseCase().flatMapLatest { city ->
            observeSettingsUseCase().map { settings ->
                mutableSettingsState.update { settingsState ->
                    val state = if (city == null || settings == null) {
                        SettingsState.State.ERROR
                    } else {
                        SettingsState.State.SUCCESS
                    }

                    settingsState.copy(
                        settings = settings,
                        selectedCity = city,
                        state = state
                    )
                }
            }
        }.launchIn(sharedScope)
    }

    private fun loadCityList() {
        sharedScope.launch {
            mutableSettingsState.update { settingsState ->
                settingsState.copy(cityList = getCityListUseCase())
            }
        }
    }

}
