package com.bunbeauty.papakarlo.feature.address.screen.cafe_address_list

import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.address.model.AddressItem
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.CafeAddress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CafeAddressListViewModel(
    private val cafeInteractor: ICafeInteractor,
) : BaseViewModel() {

    private val mutableCafeAddressList: MutableStateFlow<List<AddressItem>?> =
        MutableStateFlow(null)
    val cafeAddressList: StateFlow<List<AddressItem>?> = mutableCafeAddressList.asStateFlow()

    init {
        observeCafeAddressList()
    }

    private fun observeCafeAddressList() {
        cafeInteractor.observeCafeAddressList().launchOnEach { cafeAddressList ->
            mutableCafeAddressList.value = cafeAddressList.map(::toItem)
        }
    }

    private fun toItem(cafeAddress: CafeAddress): AddressItem {
        return AddressItem(
            uuid = cafeAddress.cafeUuid,
            address = cafeAddress.address,
            isClickable = true
        )
    }
}
