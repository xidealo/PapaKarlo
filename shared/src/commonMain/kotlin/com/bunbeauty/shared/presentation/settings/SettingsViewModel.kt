package com.bunbeauty.shared.presentation.settings

import com.bunbeauty.shared.domain.feature.city.GetCityListUseCase
import com.bunbeauty.shared.domain.feature.city.ObserveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.city.SaveSelectedCityUseCase
import com.bunbeauty.shared.domain.feature.settings.ObserveSettingsUseCase
import com.bunbeauty.shared.domain.feature.settings.UpdateEmailUseCase
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
) : SharedViewModel() {

    private val mutableSettingsState = MutableStateFlow(SettingsState())
    val settingsState = mutableSettingsState.asStateFlow()

    init {
        observeSettings()
        loadCityList()
    }

    fun onEmailClicked() {
        mutableSettingsState.update { settingsState ->
            settingsState + SettingsState.Event.ShowEditEmailEvent(settingsState.settings?.email)
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
            settingsState + SettingsState.Event.ShowCityListEvent(settingsState.cityList)
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

    private fun observeSettings() {
        observeSelectedCityUseCase().flatMapLatest { city ->
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
