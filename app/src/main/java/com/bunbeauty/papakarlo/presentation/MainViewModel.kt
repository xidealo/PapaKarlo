package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    fun refreshData(
        @Api cityRepo: CityRepo,
        @Api menuProductRepo: MenuProductRepo,
        @Api deliveryRepo: DeliveryRepo,
        @Api userRepo: UserRepo,
    ) {
        viewModelScope.launch {
            cityRepo.refreshCityList()
            menuProductRepo.refreshMenuProductList()
            deliveryRepo.refreshDelivery()
            userRepo.refreshUser()
        }
    }
}