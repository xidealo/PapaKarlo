package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.local.db.street.StreetRepo
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.model.Street
import com.bunbeauty.papakarlo.view_model.base.ToolbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    private val addressRepo: AddressRepo,
    private val iDataStoreHelper: IDataStoreHelper,
    private val streetRepo: StreetRepo
) : ToolbarViewModel() {

    var streets = listOf<Street>()
    var streetNamesFiled = ObservableField<List<String>>()

    fun getStreets() {
        viewModelScope.launch {
            streetRepo.getStreets().collect { streetsFlow ->
                streetNamesFiled.set(streetsFlow.map { it.name })
                streets = streetsFlow
            }
        }
    }

    fun onCreateAddressClicked(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {

            val addressId = addressRepo.insert(address)
            iDataStoreHelper.saveDeliveryAddressId(addressId)
            withContext(Dispatchers.Main) {
                router.navigateUp()
            }
        }
    }

    fun isCorrectFieldContent(text: String, isRequired: Boolean, maxLength: Int): Boolean {
        if (text.isEmpty() && isRequired) {
            return false
        }

        if (text.length > maxLength) {
            return false
        }

        return true
    }
}