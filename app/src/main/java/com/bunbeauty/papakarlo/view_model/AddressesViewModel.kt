package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
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
    private val iDataStoreHelper: IDataStoreHelper
) : BaseViewModel() {

    var navigator: WeakReference<AddressesNavigator>? = null
    var isDelivery: Boolean = true
    var hasAddresses = ObservableField(true)

    private val deliveryAddressesLiveData by lazy {
        Transformations.map(addressRepo.getNotCafeAddresses()) {
            if (it.isEmpty())
                hasAddresses.set(false)
            else
                hasAddresses.set(true)

            it.reversed()
        }
    }

    fun getAddressesLiveData(isDelivery: Boolean): LiveData<List<Address>> {
        this.isDelivery = isDelivery
        return if (isDelivery)
            deliveryAddressesLiveData
        else
            addressRepo.getCafeAddresses()
    }

    fun saveSelectedAddress(address: Address) {
        viewModelScope.launch {
            if (isDelivery)
                iDataStoreHelper.saveSelectedDeliveryAddress(address)
            else
                iDataStoreHelper.saveSelectedPickupAddress(address)

            navigator?.get()?.goToBack()
        }
    }

    fun createAddressClick() {
        navigator?.get()?.goToCreationAddress()
    }
}