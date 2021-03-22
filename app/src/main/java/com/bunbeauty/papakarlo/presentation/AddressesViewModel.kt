package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.utils.IDataStoreHelper
import com.bunbeauty.data.model.Address
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressesViewModel @Inject constructor(
    private val addressRepo: AddressRepo,
    private val dataStoreHelper: IDataStoreHelper
) : BaseViewModel() {

    var isDelivery = false

    val addressesLiveData by lazy {
        if (isDelivery) {
            Transformations.map(addressRepo.getNotCafeAddresses()) {
                it.reversed()
            }
        } else {
            addressRepo.getCafeAddresses()
        }
    }

    fun saveSelectedAddress(address: Address) {
        viewModelScope.launch {
            if (isDelivery)
                dataStoreHelper.saveDeliveryAddressId(address.id)
            else {
                dataStoreHelper.saveCafeId(address.cafeId!!)
            }

            router.navigateUp()
        }
    }

    fun createAddressClick() {
        router.navigate(toCreationAddressFragment())
    }
}