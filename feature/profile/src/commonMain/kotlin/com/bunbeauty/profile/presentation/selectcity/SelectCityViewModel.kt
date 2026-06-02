package com.bunbeauty.profile.presentation.selectcity

import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.city.ICityInteractor
import com.bunbeauty.core.domain.splash.CheckOneCityUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.city.City

class SelectCityViewModel(
    private val cityInteractor: ICityInteractor,
    private val checkOneCityUseCase: CheckOneCityUseCase,
) : SharedStateViewModel<SelectCityDataState.DataState, SelectCityDataState.Action, SelectCityDataState.Event>(
        initDataState =
            SelectCityDataState.DataState(
                state = SelectCityDataState.DataState.State.LOADING,
                cityList = emptyList(),
            ),
    ) {
    override fun reduce(
        action: SelectCityDataState.Action,
        dataState: SelectCityDataState.DataState,
    ) {
        when (action) {
            is SelectCityDataState.Action.OnCitySelected -> onCitySelected(city = action.city)
            SelectCityDataState.Action.OnContinueClicked -> onContinueClicked(dataState = dataState)
            SelectCityDataState.Action.OnRefreshClicked -> getCityList()
            SelectCityDataState.Action.GetCityList -> getCityList()
        }
    }

    private fun getCityList() {
        setState { copy(state = SelectCityDataState.DataState.State.LOADING) }
        sharedScope.launchSafe(
            block = {
                val cities = cityInteractor.getCityList().orEmpty()
                if (cities.isEmpty()) {
                    setState {
                        copy(state = SelectCityDataState.DataState.State.ERROR)
                    }
                    return@launchSafe
                }
                val contentMode =
                    if (checkOneCityUseCase(cities)) {
                        SelectCityDataState.DataState.ContentMode.SingleCity
                    } else {
                        SelectCityDataState.DataState.ContentMode.CityList
                    }
                setState {
                    copy(
                        state = SelectCityDataState.DataState.State.SUCCESS,
                        cityList = cities,
                        contentMode = contentMode,
                    )
                }
            },
            onError = {
                setState {
                    copy(state = SelectCityDataState.DataState.State.ERROR)
                }
            },
        )
    }

    private fun onContinueClicked(dataState: SelectCityDataState.DataState) {
        val city = dataState.cityList.firstOrNull() ?: return
        onCitySelected(city = city)
    }

    private fun onCitySelected(city: City) {
        sharedScope.launchSafe(
            block = {
                cityInteractor.saveSelectedCity(city)
                addEvent {
                    SelectCityDataState.Event.NavigateToMenu
                }
            },
            onError = {
                setState {
                    copy(state = SelectCityDataState.DataState.State.ERROR)
                }
            },
        )
    }
}
