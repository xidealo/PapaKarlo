package com.bunbeauty.papakarlo.presentation.address

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.model.local.Street
import com.bunbeauty.domain.model.local.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.domain.repo.StreetRepo
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    private val userAddressRepo: UserAddressRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val streetRepo: StreetRepo,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

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
            userAddress.userId = dataStoreRepo.userUuid.first()
            val uuid = if (!userAddress.userId.isNullOrEmpty()) {
                userAddressRepo.insert("token", userAddress).uuid
            } else {
                userAddress.uuid = UUID.randomUUID().toString()
                userAddressRepo.insert(userAddress)
                userAddress.uuid
            }
            dataStoreRepo.saveDeliveryAddressId(uuid)
            withContext(Dispatchers.Main) {
                showMessage(resourcesProvider.getString(R.string.msg_creation_address_created_address))
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