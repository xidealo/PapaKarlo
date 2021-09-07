package com.bunbeauty.papakarlo.presentation.address

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.item.AddressItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


abstract class AddressesViewModel : BaseViewModel() {
    abstract val addressListState: StateFlow<State<List<AddressItem>>>

    abstract fun saveSelectedAddress(addressUuid: String, isDelivery: Boolean)
    abstract fun onCreateAddressClicked()
    abstract fun getAddresses(isDelivery: Boolean)
}

class AddressesViewModelImpl @Inject constructor(
    @Firebase private val cafeRepo: CafeRepo,
    @Firebase private val userAddressRepo: UserAddressRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
) : AddressesViewModel() {

    override val addressListState: MutableStateFlow<State<List<AddressItem>>> =
        MutableStateFlow(State.Loading())

    override fun getAddresses(isDelivery: Boolean) {
        val userUuid = runBlocking {
            dataStoreRepo.userUuid.first()
        }
        if (isDelivery) {
            if (userUuid != null) {
                userAddressRepo.observeUserAddressListByUserUuid(userUuid)
                    .onEach { userAddressList ->
                        addressListState.value = userAddressList.map { userAddress ->
                            userAddress.toItem()
                        }.toStateSuccess()
                    }.launchIn(viewModelScope)
            }
        } else {
            cafeRepo.observeCafeAddressList().onEach { cafeAddressList ->
                addressListState.value = cafeAddressList.map { cafeAddress ->
                    cafeAddress.toItem()
                }.toStateSuccess()
            }.launchIn(viewModelScope)
        }
    }

    override fun saveSelectedAddress(addressUuid: String, isDelivery: Boolean) {
        viewModelScope.launch {
            if (isDelivery) {
                dataStoreRepo.saveUserAddressUuid(addressUuid)
            } else {
                dataStoreRepo.saveCafeAddressUuid(addressUuid)
            }
            goBack()
        }
    }

    override fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    private fun UserAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = this.uuid,
            address = stringUtil.getUserAddressString(this)
        )
    }

    private fun CafeAddress.toItem(): AddressItem {
        return AddressItem(
            uuid = this.cafeUuid,
            address = stringUtil.getCafeAddressString(this)
        )
    }
}