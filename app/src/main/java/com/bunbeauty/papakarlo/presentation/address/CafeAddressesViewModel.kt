package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.item.AddressItem
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CafeAddressesViewModel @Inject constructor(
    @Api private val cafeRepo: CafeRepo,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableCafeAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val cafeAddressList: StateFlow<List<AddressItem>> = mutableCafeAddressList.asStateFlow()

    init {
        subscribeOnCafeAddressList()
    }

    private fun subscribeOnCafeAddressList() {
        cafeRepo.observeCafeAddressList().onEach { cafeAddressList ->
            mutableCafeAddressList.value = cafeAddressList.map { cafeAddress ->
                cafeAddress.toItem()
            }
        }.launchIn(viewModelScope)
    }

    private fun CafeAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = cafeUuid,
            address = stringUtil.getCafeAddressString(this)
        )
    }
}