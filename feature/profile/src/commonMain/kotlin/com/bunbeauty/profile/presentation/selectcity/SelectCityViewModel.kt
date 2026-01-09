package com.bunbeauty.profile.presentation.selectcity

import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.city.ICityInteractor

class SelectCityViewModel(
    private val cityInteractor: ICityInteractor,
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
            SelectCityDataState.Action.OnRefreshClicked -> getCityList()
            SelectCityDataState.Action.GetCityList -> getCityList()
        }
    }

    private fun getCityList() {
        setState { copy(state = SelectCityDataState.DataState.State.LOADING) }
        sharedScope.launchSafe(
            block = {
                setState {
                    copy(
                        state = SelectCityDataState.DataState.State.SUCCESS,
                        cityList = cityInteractor.getCityList() ?: emptyList(),
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
