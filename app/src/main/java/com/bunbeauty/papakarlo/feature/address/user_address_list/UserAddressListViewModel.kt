package com.bunbeauty.papakarlo.feature.address.user_address_list

import androidx.lifecycle.SavedStateHandle
import com.bunbeauty.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.domain.model.address.UserAddress
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.address.AddressItem
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

    private val mutableAddressListState: MutableStateFlow<State<List<AddressItem>>> =
        MutableStateFlow(State.Loading())
    val addressListState: StateFlow<State<List<AddressItem>>> =
        mutableAddressListState.asStateFlow()

    init {
        observeUserAddressList()
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    private fun observeUserAddressList() {
        addressInteractor.observeAddressList().launchOnEach { userAddressList ->
            mutableAddressListState.value = userAddressList.map(::toItem).toState()
        }
    }

    private fun toItem(userAddress: UserAddress): AddressItem {
        return AddressItem(
            uuid = userAddress.uuid,
            address = stringUtil.getUserAddressString(userAddress) ?: "",
            isClickable = isClickable
        )
    }
}