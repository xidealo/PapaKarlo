package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toSuccessOrEmpty
import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.SelectCityFragmentDirections.toMenuFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    @Api private val cityRepo: CityRepo,
    @Api private val cafeRepo: CafeRepo,
    @Api private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo,
) : BaseViewModel() {

    private val mutableCityListState: MutableStateFlow<State<List<City>>> =
        MutableStateFlow(State.Loading())
    val cityListState: StateFlow<State<List<City>>> = mutableCityListState.asStateFlow()

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
        mutableCityListState.value = State.Loading()
        viewModelScope.launch {
            dataStoreRepo.saveSelectedCityUuid(city.uuid)
            cafeRepo.refreshCafeList()
            streetRepo.refreshStreetList()
            router.navigate(toMenuFragment())
        }
    }

    private fun subscribeOnCityList() {
        cityRepo.observeCityList().onEach { cityList ->
            mutableCityListState.value = cityList.toSuccessOrEmpty()
        }.launchIn(viewModelScope)

        // TODO for test
        viewModelScope.launch {
            delay(2500)
            mutableCityListState.value = listOf(
                City("1", "Москва"),
                City("2", "Дубна"),
                City("2", "Кимры")
            ).toSuccessOrEmpty()
        }
    }
}