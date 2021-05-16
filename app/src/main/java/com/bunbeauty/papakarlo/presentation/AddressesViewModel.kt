package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.model.address.Address
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.address.UserAddressRepo
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


abstract class AddressesViewModel : BaseViewModel() {
    abstract val addressListState: StateFlow<State<List<Address>>>

    abstract fun saveSelectedAddress(addressId: String, isDelivery: Boolean)
    abstract fun onCreateAddressClicked()
    abstract fun getAddresses(isDelivery: Boolean)
}

class AddressesViewModelImpl @Inject constructor(
    private val cafeAddressRepo: CafeAddressRepo,
    private val userAddressRepo: UserAddressRepo,
    private val dataStoreHelper: IDataStoreHelper
) : AddressesViewModel() {

    override val addressListState: MutableStateFlow<State<List<Address>>> =
        MutableStateFlow(State.Loading())

    override fun getAddresses(isDelivery: Boolean) {
        val userId = runBlocking {
            dataStoreHelper.userId.first()
        }
        if (isDelivery) {
            userAddressRepo.getUserAddressByUserId(userId).onEach {
                addressListState.value = it.toStateSuccess()
            }.launchIn(viewModelScope)
        } else {
            cafeAddressRepo.getCafeAddresses().onEach {
                addressListState.value = it.toStateSuccess()
            }.launchIn(viewModelScope)
        }
    }

    override fun saveSelectedAddress(addressId: String, isDelivery: Boolean) {
        viewModelScope.launch {
            if (isDelivery) {
                dataStoreHelper.saveDeliveryAddressId(addressId)
            } else {
                dataStoreHelper.saveCafeAddressId(addressId)
            }
            router.navigateUp()
        }
    }

    override fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }
}