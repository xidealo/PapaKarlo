package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragmentDirections.toMenuFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    @Api private val cityRepo: CityRepo,
    private val dataStoreRepo: DataStoreRepo,
) : BaseViewModel() {

    private val mutableCityList: MutableStateFlow<List<City>?> = MutableStateFlow(null)
    val cityList: StateFlow<List<City>?> = mutableCityList.asStateFlow()

    init {
        subscribeOnCityList()
    }

    fun checkIsCitySelected() {
        viewModelScope.launch {
            val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()
            if (selectedCityUuid != null) {
                router.navigate(toMenuFragment())
            }
        }
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            dataStoreRepo.saveSelectedCityUuid(city.uuid)
            router.navigate(toMenuFragment())
        }
    }

    private fun subscribeOnCityList() {
        cityRepo.observeCityList().onEach { cityList ->
            if (cityList.isNotEmpty()) {
                mutableCityList.value = cityList
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            delay(2000)
            mutableCityList.value = listOf(City("1", "Кимры"), City("2", "Дубна"))
        }
    }
}