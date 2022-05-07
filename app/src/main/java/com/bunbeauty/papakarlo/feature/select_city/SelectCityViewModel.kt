package com.bunbeauty.papakarlo.feature.select_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.select_city.SelectCityFragmentDirections.toMenuFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectCityViewModel(
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    private val mutableCityListState: MutableStateFlow<State<List<City>>> =
        MutableStateFlow(State.Loading())
    val cityListState: StateFlow<State<List<City>>> = mutableCityListState.asStateFlow()

    fun getCityList() {
        mutableCityListState.value = State.Loading()
        viewModelScope.launch {
            mutableCityListState.value = cityInteractor.getCityList()
                .toState(resourcesProvider.getString(R.string.error_select_city_loading))
        }
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }
}