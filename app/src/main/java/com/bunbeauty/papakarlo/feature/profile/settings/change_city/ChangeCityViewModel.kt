package com.bunbeauty.papakarlo.feature.profile.settings.change_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChangeCityViewModel  constructor(
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    private val mutableCityList: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val cityList: StateFlow<List<City>> = mutableCityList.asStateFlow()

    init {
        subscribeOnCityList()
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            goBack()
        }
    }

    private fun subscribeOnCityList() {
        cityInteractor.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList
        }.launchIn(viewModelScope)
    }
}