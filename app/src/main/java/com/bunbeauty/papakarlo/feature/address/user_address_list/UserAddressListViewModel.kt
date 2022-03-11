package com.bunbeauty.papakarlo.feature.address.user_address_list

import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.address.AddressItemModel
import com.bunbeauty.papakarlo.feature.address.user_address_list.UserAddressListBottomSheetDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserAddressListViewModel(
    private val addressInteractor: IAddressInteractor,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableAddressItemModelList: MutableStateFlow<List<AddressItemModel>> =
        MutableStateFlow(emptyList())
    val addressItemModelList: StateFlow<List<AddressItemModel>> =
        mutableAddressItemModelList.asStateFlow()

    init {
        observeUserAddressList()
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    private fun observeUserAddressList() {
        addressInteractor.observeAddressList().launchOnEach { userAddressList ->
            mutableAddressItemModelList.value = userAddressList.map { userAddress ->
                userAddress.toItem()
            }
        }
    }

    private fun UserAddress.toItem(): AddressItemModel {
        return AddressItemModel(
            uuid = uuid,
            address = stringUtil.getUserAddressString(this) ?: ""
        )
    }
}