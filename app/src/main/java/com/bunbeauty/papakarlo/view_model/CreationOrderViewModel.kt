package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.utils.string.IStringHelper
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreHelper: IDataStoreHelper,
    private val resourcesProvider: IResourcesProvider,
    private val iStringHelper: IStringHelper,
    private val addressRepo: AddressRepo,
    private val cafeRepo: CafeRepo
) : BaseViewModel() {
    var navigator: WeakReference<CreationOrderNavigator>? = null

    val errorMessageLiveData = MutableLiveData<String>()
    val lastAddressField = ObservableField("")
    val isDeliveryField = ObservableField(true)
    val showCreateAddressField = ObservableField(false)
    var currentDeliveryAddress: Address? = null
    var currentPickUpAddress: Address? = null

    fun getLastDeliveryAddress() {
        if (currentDeliveryAddress != null) {
            lastAddressField.set(iStringHelper.toString(currentDeliveryAddress!!))
            lastAddressField.notifyChange()
            return
        }

        viewModelScope.launch {
            dataStoreHelper.selectedDeliveryAddress.collect { addressId ->

                addressRepo.getAddress(addressId).collect {
                    if (it?.street == null) {
                        lastAddressField.set("")
                        showCreateAddressField.set(true)
                    } else {
                        currentDeliveryAddress = it
                        lastAddressField.set(
                            iStringHelper.toString(it)
                        )
                    }
                }
            }
        }
    }

    fun getLastPickupAddress() {
        if (currentPickUpAddress != null) {
            lastAddressField.set(iStringHelper.toString(currentPickUpAddress!!))
            return
        }

        viewModelScope.launch {
            dataStoreHelper.selectedPickupAddress.collect { addressId ->
                addressRepo.getAddress(addressId).collect {
                    if (it?.street == null) {
                        lastAddressField.set("")
                    } else {
                        currentPickUpAddress = it
                        lastAddressField.set(iStringHelper.toString(it))
                    }
                }
            }
        }
    }

    fun createOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            val address = if (isDeliveryField.get()!!) {
                currentDeliveryAddress
            } else {
                currentPickUpAddress
            }
            if (address == null) {
                errorMessageLiveData.value =
                    resourcesProvider.getString(R.string.error_creation_order_address)
                return@launch
            } else {
                orderEntity.address = address
            }

            orderEntity.isDelivery = isDeliveryField.get()!!
            orderEntity.code = generateCode()
            val order = Order(
                orderEntity,
                cartProductRepo.getCartProductListAsync().await(),
                cafeRepo.getCafeEntityByDistrict(orderEntity.address.street?.districtId ?: "ERROR CAFE").await().id
            )

            orderRepo.saveOrder(order)
            navigator?.get()?.goToMain(orderEntity)
        }
    }

    fun generateCode(): String {
        val number = (DateTime.now().secondOfDay % CODE_COUNT)
        val letters = resourcesProvider.getString(R.string.code_letters)
        val letter = letters[number % letters.length]

        return letter.toString() + (number / letters.length)
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

    fun isCorrectFieldContent(
        text: String,
        isRequired: Boolean,
        minLength: Int,
        maxLength: Int
    ): Boolean {
        if (!isCorrectFieldContent(text, isRequired, maxLength)) {
            return false
        }

        if (text.length < minLength) {
            return false
        }

        return true
    }

    fun onOrderClick() {
        navigator?.get()?.createDeliveryOrder()
    }

    fun onAddAddressClick() {
        navigator?.get()?.goToCreationAddress()
    }

    companion object {
        private const val CODE_COUNT = 2400
    }
}