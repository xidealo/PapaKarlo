package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ProfileFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val addressRepo: AddressRepo
) : ToolbarViewModel() {

    val userIdFlow by lazy { dataStoreHelper.userId }

    fun getAddress(): MutableStateFlow<State<Address?>> {
        val addressStateFlow = MutableStateFlow<State<Address?>>(State.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreHelper.deliveryAddressId.collect { deliveryAddressId ->
                addressRepo.getAddressById(deliveryAddressId).collect {
                    addressStateFlow.emit(it.toStateNullableSuccess())
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

    fun goToLogin() {
        router.navigate(ProfileFragmentDirections.toLoginFragment())
    } 
    
    fun logout() {
        viewModelScope.launch {
            dataStoreHelper.saveUserId("")
        }
    }
}