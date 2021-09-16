package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.*
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    fun refreshData(
        @Api cityRepo: CityRepo,
        @Api cafeRepo: CafeRepo,
        @Api streetRepo: StreetRepo,
        @Api menuProductRepo: MenuProductRepo,
        @Api deliveryRepo: DeliveryRepo,
        @Api userRepo: UserRepo,
    ) {
        viewModelScope.launch {
            cityRepo.refreshCityList()
            cafeRepo.refreshCafeList()
            streetRepo.refreshStreetList()

            menuProductRepo.refreshMenuProductList()
            deliveryRepo.refreshDelivery()

            userRepo.refreshUser()
        }
    }
}