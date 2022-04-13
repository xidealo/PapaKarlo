package com.bunbeauty.papakarlo.feature.address.user_address_list

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.toSuccessOrEmpty
import com.bunbeauty.papakarlo.feature.address.AddressItemModel
import com.bunbeauty.papakarlo.feature.address.user_address_list.UserAddressListFragmentDirections.toCreateAddressFragment
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserAddressListViewModel(
    private val addressInteractor: IAddressInteractor,
    private val stringUtil: IStringUtil,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val isClickable = savedStateHandle["isClickable"] ?: false

    private val mutableAddressItemModelList: MutableStateFlow<State<List<AddressItemModel>>> =
        MutableStateFlow(State.Loading())
    val addressItemModelList: StateFlow<State<List<AddressItemModel>>> =
        mutableAddressItemModelList.asStateFlow()

    init {
        observeUserAddressList()
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    private fun observeUserAddressList() {
        addressInteractor.observeAddressList().launchOnEach { userAddressList ->
            mutableAddressItemModelList.value = userAddressList.map(::toItem).toSuccessOrEmpty()
        }
    }

    private fun toItem(userAddress: UserAddress): AddressItemModel {
        return AddressItemModel(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isClickable = isClickable
        )
    }
}