package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragmentDirections.toMenuFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    private val cityInteractor: ICityInteractor,
) : BaseViewModel() {

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    private val mutableCityList: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val cityList: StateFlow<List<City>> = mutableCityList.asStateFlow()

    init {
        viewModelScope.launch {
            cityInteractor.checkIsCitySelected()
        }

        checkIsCitySelected()
        subscribeOnCityList()
    }

    private fun checkIsCitySelected() {
        viewModelScope.launch {
            val isCitySelected = cityInteractor.checkIsCitySelected()
            mutableIsLoading.value = isCitySelected
            if (isCitySelected) {
                router.navigate(toMenuFragment())
            }
        }
    }

    fun onCitySelected(city: City) {
        mutableIsLoading.value = true
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }

    private fun subscribeOnCityList() {
        cityInteractor.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList
        }.launchIn(viewModelScope)
    }
}