package com.bunbeauty.papakarlo.feature.select_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.select_city.SelectCityFragmentDirections.toMenuFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    private val mutableCityList: MutableStateFlow<List<City>?> = MutableStateFlow(null)
    val cityList: StateFlow<List<City>?> = mutableCityList.asStateFlow()

    init {
        observeCityList()
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }

    private fun observeCityList() {
        cityInteractor.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList
        }.launchIn(viewModelScope)
    }
}