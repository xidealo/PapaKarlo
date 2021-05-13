package com.bunbeauty.papakarlo.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.Street
import com.bunbeauty.data.model.address.UserAddress
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.address.UserAddressRepo
import com.bunbeauty.domain.repository.street.StreetRepo
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    private val userAddressRepo: UserAddressRepo,
    private val iDataStoreHelper: IDataStoreHelper,
    private val streetRepo: StreetRepo,
    private val resourcesProvider: IResourcesProvider
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

    fun onCreateAddressClicked(userAddress: UserAddress) {
        viewModelScope.launch(Dispatchers.IO) {
            userAddress.userId = iDataStoreHelper.userId.first()
            val userAddressWithId = userAddressRepo.insert("token", userAddress)

            iDataStoreHelper.saveDeliveryAddressId(userAddressWithId.uuid)
            withContext(Dispatchers.Main) {
                messageSharedFlow.emit(resourcesProvider.getString(R.string.msg_creation_address_created_address))
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