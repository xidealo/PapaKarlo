package com.bunbeauty.papakarlo.feature.profile.settings.change_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangeCityViewModel(
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
        cityInteractor.observeCityList().launchOnEach { cityList ->
            mutableCityList.value = cityList
        }
    }
}