package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
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

    private val mutableCityList: MutableStateFlow<State<List<City>>> =
        MutableStateFlow(State.Loading())
    val cityList: StateFlow<State<List<City>>> = mutableCityList.asStateFlow()

    init {
        viewModelScope.launch {
            cityInteractor.checkIsCitySelected()
        }

        checkIsCitySelected()
    }

    fun onCitySelected(city: City) {
        mutableCityList.value = State.Loading()
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }

    private fun checkIsCitySelected() {
        viewModelScope.launch {
            val isCitySelected = cityInteractor.checkIsCitySelected()
            if (isCitySelected) {
                router.navigate(toMenuFragment())
            } else {
                mutableCityList.value = State.Empty()
                observeCityList()
            }
        }
    }

    private fun observeCityList() {
        cityInteractor.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList.toSuccessOrEmpty()
        }.launchIn(viewModelScope)
    }
}