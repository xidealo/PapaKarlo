package com.bunbeauty.papakarlo.feature.address.cafe_address_list

import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.CafeAddress
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.address.AddressItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CafeAddressListViewModel(
    private val cafeInteractor: ICafeInteractor,
) : BaseViewModel() {

    private val mutableCafeAddressList: MutableStateFlow<List<AddressItemModel>?> =
        MutableStateFlow(null)
    val cafeAddressList: StateFlow<List<AddressItemModel>?> = mutableCafeAddressList.asStateFlow()

    init {
        observeCafeAddressList()
    }

    private fun observeCafeAddressList() {
        cafeInteractor.observeCafeAddressList().launchOnEach { cafeAddressList ->
            mutableCafeAddressList.value = cafeAddressList.map(::toItem)
        }
    }

    private fun toItem(cafeAddress: CafeAddress): AddressItemModel {
        return AddressItemModel(
            uuid = cafeAddress.cafeUuid,
            address = cafeAddress.address
        )
    }
}