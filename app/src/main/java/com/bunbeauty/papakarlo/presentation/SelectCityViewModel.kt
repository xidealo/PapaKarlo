package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragmentDirections.toMenuFragment
import com.example.data_api.Api
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    @Api private val cityRepo: CityRepo,
    @Api private val cafeRepo: CafeRepo,
    @Api private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
) : BaseViewModel() {

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    private val mutableCityList: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val cityList: StateFlow<List<City>> = mutableCityList.asStateFlow()

    init {
        subscribeOnCityList()
    }

    fun checkIsCitySelected() {
        viewModelScope.launch {
            val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()
            if (selectedCityUuid == null) {
                mutableIsLoading.value = false
            } else {
                cafeRepo.refreshCafeList()
                streetRepo.refreshStreetList()
                router.navigate(toMenuFragment())
            }
        }
    }

    fun onCitySelected(city: City) {
        mutableIsLoading.value = true
        viewModelScope.launch {
            dataStoreRepo.saveSelectedCityUuid(city.uuid)
            cafeRepo.refreshCafeList()
            streetRepo.refreshStreetList()
            router.navigate(toMenuFragment())
        }
    }

    private fun subscribeOnCityList() {
        cityRepo.observeCityList().onEach { cityList ->
            mutableCityList.value = cityList
        }.launchIn(viewModelScope)
    }
}