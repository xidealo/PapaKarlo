package com.bunbeauty.papakarlo.presentation.profile.settings

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitySelectionViewModel @Inject constructor(
    @Api private val cityRepo: CityRepo,
    @Api private val cafeRepo: CafeRepo,
    @Api private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
) : BaseViewModel() {

    private val mutableCityList: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val cityList: StateFlow<List<City>> = mutableCityList.asStateFlow()

    init {
        subscribeOnCityList()
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            dataStoreRepo.saveSelectedCityUuid(city.uuid)
            cafeRepo.refreshCafeList()
            streetRepo.refreshStreetList()
            goBack()
        }
    }

    private fun subscribeOnCityList() {
        cityRepo.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList
        }.launchIn(viewModelScope)
    }
}