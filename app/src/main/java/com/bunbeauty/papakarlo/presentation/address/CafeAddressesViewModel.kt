package com.bunbeauty.papakarlo.presentation.address

import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.CafeAddress
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.item.AddressItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CafeAddressesViewModel @Inject constructor(
    private val cafeInteractor: ICafeInteractor,
) : BaseViewModel() {

    private val mutableCafeAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val cafeAddressList: StateFlow<List<AddressItem>> = mutableCafeAddressList.asStateFlow()

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
            address = cafeAddress.address
        )
    }
}