package com.bunbeauty.papakarlo.feature.select_city

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toStateSuccessOrError
import com.bunbeauty.papakarlo.feature.select_city.SelectCityFragmentDirections.toMenuFragment
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SelectCityViewModel(
    private val cityInteractor: ICityInteractor,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    private val mutableCityListState: MutableStateFlow<StateWithError<List<City>>> =
        MutableStateFlow(StateWithError.Loading())
    val cityListState: StateFlow<StateWithError<List<City>>> = mutableCityListState.asStateFlow()

    fun getCityList() {
        mutableCityListState.value = StateWithError.Loading()
        viewModelScope.launch {
            mutableCityListState.value = cityInteractor.getCityList()
                .toStateSuccessOrError(resourcesProvider.getString(R.string.error_select_city_loading))
        }
    }

    fun onCitySelected(city: City) {
        viewModelScope.launch {
            cityInteractor.saveSelectedCity(city)
            router.navigate(toMenuFragment())
        }
    }
}