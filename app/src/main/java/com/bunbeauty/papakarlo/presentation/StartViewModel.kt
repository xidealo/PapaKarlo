package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.presentation.state.StartScreenState
import com.bunbeauty.papakarlo.ui.fragment.StartFragmentDirections.toMenuFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val cityInteractor: ICityInteractor,
    private val updateInteractor: IUpdateInteractor,
) : BaseViewModel() {

    private val mutableStartScreenState: MutableStateFlow<StartScreenState> =
        MutableStateFlow(StartScreenState.Loading)
    val startScreenState: StateFlow<StartScreenState> = mutableStartScreenState.asStateFlow()

    init {
        checkIsUpdated()
    }

    fun onCitySelected(city: City) {
        mutableStartScreenState.value = StartScreenState.Loading
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }

    private fun checkIsUpdated() {
        viewModelScope.launch {
            val isUpdated = updateInteractor.checkIsUpdated(BuildConfig.VERSION_CODE)
            if (isUpdated) {
                checkIsCitySelected()
            } else {
                mutableStartScreenState.value = StartScreenState.NotUpdated
            }
        }
    }

    private suspend fun checkIsCitySelected() {
        val isCitySelected = cityInteractor.checkIsCitySelected()
        if (isCitySelected) {
            router.navigate(toMenuFragment())
        } else {
            observeCityList()
        }
    }

    private fun observeCityList() {
        cityInteractor.observeCityList().onEach { cityList ->
            mutableStartScreenState.value = StartScreenState.CitiesLoaded(cityList)
        }.launchIn(viewModelScope)
    }
}