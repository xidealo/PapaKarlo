package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val addressRepo: AddressRepo
) : ToolbarViewModel() {

    val addressStateFlow by lazy {
        runBlocking {
            addressRepo.getAddressByCafeId(dataStoreHelper.cafeId.first())
        }
    }

    val phoneNumber by lazy {
        runBlocking {
            dataStoreHelper.phoneNumber.first()
        }
    }
    val email by lazy {
        runBlocking {
            dataStoreHelper.email.first()
        }
    }
}