package com.bunbeauty.papakarlo.feature.address.user_address_list

import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.address.AddressItem
import com.bunbeauty.papakarlo.feature.address.user_address_list.UserAddressListBottomSheetDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserAddressListViewModel  constructor(
    private val addressInteractor: IAddressInteractor,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableUserAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val userAddressList: StateFlow<List<AddressItem>> = mutableUserAddressList.asStateFlow()

    init {
        observeUserAddressList()
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    private fun observeUserAddressList() {
        addressInteractor.observeAddressList().launchOnEach { userAddressList ->
            mutableUserAddressList.value = userAddressList.map { userAddress ->
                userAddress.toItem()
            }
        }
    }

    private fun UserAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = uuid,
            address = stringUtil.getUserAddressString(this) ?: ""
        )
    }
}