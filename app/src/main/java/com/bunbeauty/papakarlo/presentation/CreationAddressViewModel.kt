package com.bunbeauty.papakarlo.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.model.Street
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.domain.repository.street.StreetRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            address.userId = iDataStoreHelper.userId.first()
            val addressId = addressRepo.insert("token", address)
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