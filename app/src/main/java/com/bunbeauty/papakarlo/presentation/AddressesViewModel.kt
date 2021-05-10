package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressesViewModel @Inject constructor(
    private val cafeAddressRepo: CafeAddressRepo,
    private val dataStoreHelper: IDataStoreHelper
) : BaseViewModel() {

    var isDelivery = false

    /*val addressesLiveData by lazy {
        if (isDelivery) {
            Transformations.map(cafeAddressRepo.getNotCafeAddresses()) {
                it.reversed()
            }
        } else {
            cafeAddressRepo.getCafeAddresses()
        }
    }
*/
    fun saveSelectedAddress(cafeAddress: CafeAddress) {
        viewModelScope.launch {
            if (isDelivery)
                dataStoreHelper.saveDeliveryAddressId(cafeAddress.id)
            else {
                dataStoreHelper.saveCafeId(cafeAddress.cafeId!!)
            }
            router.navigateUp()
        }
    }

    fun createAddressClick() {
        router.navigate(toCreationAddressFragment())
    }
}