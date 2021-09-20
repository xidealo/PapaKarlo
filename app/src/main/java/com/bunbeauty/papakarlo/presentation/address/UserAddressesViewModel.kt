package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.address.UserAddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.presentation.item.AddressItem
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserAddressesViewModel @Inject constructor(
    @Api private val userAddressRepo: UserAddressRepo,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableUserAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val userAddressList: StateFlow<List<AddressItem>> = mutableUserAddressList.asStateFlow()

    init {
        subscribeOnUserAddressList()
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    private fun subscribeOnUserAddressList() {
        userAddressRepo.observeUserAddressList().onEach { userAddressList ->
            mutableUserAddressList.value = userAddressList.map { userAddress ->
                userAddress.toItem()
            }
        }.launchIn(viewModelScope)
    }

    private fun UserAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = uuid,
            address = stringUtil.getUserAddressString(this)
        )
    }
}