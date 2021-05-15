package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.presentation.item.AddressItem
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AddressesViewModel @Inject constructor(
    @Api private val cafeRepo: CafeRepo,
    @Api private val userAddressRepo: UserAddressRepo,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableUserAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val userAddressList: StateFlow<List<AddressItem>> = mutableUserAddressList.asStateFlow()

    private val mutableCafeAddressList: MutableStateFlow<List<AddressItem>> =
        MutableStateFlow(emptyList())
    val cafeAddressList: StateFlow<List<AddressItem>> = mutableUserAddressList.asStateFlow()

    init {
        subscribeOnUserAddressList()
        subscribeOnCafeAddressList()
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

    private fun subscribeOnCafeAddressList() {
        cafeRepo.observeCafeAddressList().onEach { cafeAddressList ->
            mutableCafeAddressList.value = cafeAddressList.map { cafeAddress ->
                cafeAddress.toItem()
            }
        }.launchIn(viewModelScope)
    }

    private fun UserAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = uuid,
            address = stringUtil.getUserAddressString(this)
        )
    }

    private fun CafeAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = cafeUuid,
            address = stringUtil.getCafeAddressString(this)
        )
    }
}