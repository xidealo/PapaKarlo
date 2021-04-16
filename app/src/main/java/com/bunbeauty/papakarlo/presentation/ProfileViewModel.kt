package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ProfileFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val addressRepo: AddressRepo
) : ToolbarViewModel() {

    val userId by lazy {
        runBlocking {
            dataStoreHelper.userId.first()
        }
    }

    fun getAddress(): MutableStateFlow<Address?> {
        val addressStateFlow = MutableStateFlow<Address?>(null)
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.deliveryAddressId.collect { deliveryAddressId ->
                addressRepo.getAddressById(deliveryAddressId).collect {
                    addressStateFlow.emit(it)
                }
            }
        }
        return addressStateFlow
    }

    fun onOrderListClicked() {
        router.navigate(ProfileFragmentDirections.toOrdersFragment("a"))
    }

    fun onAddressClicked() {
        router.navigate(ProfileFragmentDirections.toAddressesBottomSheet(true))
    }

    fun onCreateAddressClicked() {
        router.navigate(ProfileFragmentDirections.toCreationAddressFragment())
    }
}