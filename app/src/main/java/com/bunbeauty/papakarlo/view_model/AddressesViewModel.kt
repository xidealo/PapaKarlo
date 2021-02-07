package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.ui.addresses.AddressesNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class AddressesViewModel @Inject constructor(
    private val addressRepo: AddressRepo,
    private val dataStoreHelper: IDataStoreHelper
) : BaseViewModel() {

    var navigator: WeakReference<AddressesNavigator>? = null
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

            navigator?.get()?.goToBack()
        }
    }

    fun createAddressClick() {
        navigator?.get()?.goToCreationAddress()
    }
}