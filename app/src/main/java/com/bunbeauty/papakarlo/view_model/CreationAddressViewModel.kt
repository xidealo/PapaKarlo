package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.ui.creation_address.CreationAddressNavigator
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationAddressViewModel @Inject constructor(
    private val addressRepo: AddressRepo
) : BaseViewModel() {

    var navigator: WeakReference<CreationAddressNavigator>? = null

    fun creationAddress(address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepo.insert(address)
        }
    }

    fun creationAddressClick() {
        navigator?.get()?.createAddress()
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