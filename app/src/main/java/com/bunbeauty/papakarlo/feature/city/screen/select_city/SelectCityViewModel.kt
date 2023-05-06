package com.bunbeauty.papakarlo.feature.city.screen.select_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.extension.mapToStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SelectCityViewModel(
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    private val selectCityDataState = MutableStateFlow(SelectCityDataState())
    val cityListUiState = selectCityDataState.mapToStateFlow(viewModelScope) { state ->
        mapState(state)
    }

    fun getCityList() {
        selectCityDataState.update { state ->
            state.copy(state = SelectCityDataState.State.LOADING)
        }
        viewModelScope.launchSafe(
            block = {
                selectCityDataState.update { state ->
                    state.copy(
                        state = SelectCityDataState.State.SUCCESS,
                        cityList = cityInteractor.getCityList(),
                    )
                }
            },
            onError = {
                selectCityDataState.update { state ->
                    state.copy(state = SelectCityDataState.State.ERROR)
                }
            }
        )
    }

    fun onCitySelected(city: City) {
        viewModelScope.launchSafe(
            block = {
                cityInteractor.saveSelectedCity(city)
                selectCityDataState.update { state ->
                    state + SelectCityEvent.NavigateToMenu
                }
            },
            onError = {
                // TODO handle error
            }
        )
    }

    fun consumeEventList(eventList: List<SelectCityEvent>) {
        selectCityDataState.update { state ->
            state - eventList
        }
    }

    private fun mapState(dataState: SelectCityDataState): SelectCityUIState {
        return SelectCityUIState(
            cityListState = when (dataState.state) {
                SelectCityDataState.State.SUCCESS -> {
                    if (dataState.cityList == null) {
                        SelectCityUIState.CityListState.Error
                    } else {
                        SelectCityUIState.CityListState.Success(cityList = dataState.cityList)
                    }
                }
                SelectCityDataState.State.LOADING -> SelectCityUIState.CityListState.Loading
                SelectCityDataState.State.ERROR -> SelectCityUIState.CityListState.Error
            },
            eventList = dataState.eventList
        )
    }
}
