package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DeliveryRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.repo.UserRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    fun refreshData(
        cafeRepo: CafeRepo,
        menuProductRepo: MenuProductRepo,
        deliveryRepo: DeliveryRepo,
        userRepo: UserRepo
    ) {
        viewModelScope.launch {
            cafeRepo.refreshCafeList()
            //menuProductRepo.refreshMenuProducts()
            //deliveryRepo.refreshDelivery()
            //userRepo.refreshUser()
        }
    }
}