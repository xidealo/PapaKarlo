package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreHelper: IDataStoreHelper,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {
    var navigator: WeakReference<CreationOrderNavigator>? = null

    val errorMessageLiveData = MutableLiveData<String>()
    val lastAddressField = ObservableField<String>()
    val isDeliveryField = ObservableField<Boolean>()
    var currentDeliveryAddress: Address? = null
    var currentPickUpAddress: Address? = null

    fun getLastDeliveryAddress() {
        if (currentDeliveryAddress != null) {
            val selectedAddress =
                resourcesProvider.getString(R.string.msg_creation_order_selected_address)
            lastAddressField.set("$selectedAddress\n${currentDeliveryAddress!!.getAddressString()}")
            lastAddressField.notifyChange()
            return
        }

        viewModelScope.launch {
            dataStoreHelper.selectedDeliveryAddress.collect { address ->
                if (address.street.isEmpty()) {
                    val addAddress =
                        resourcesProvider.getString(R.string.msg_creation_order_add_address)
                    lastAddressField.set(addAddress)
                } else {
                    currentDeliveryAddress = address
                    val selectedAddress =
                        resourcesProvider.getString(R.string.msg_creation_order_selected_address)
                    lastAddressField.set("$selectedAddress\n${address.getAddressString()}")
                }
            }
        }
    }

    fun getLastPickupAddress() {
        if (currentPickUpAddress != null) {
            val selectedAddress =
                resourcesProvider.getString(R.string.msg_creation_order_selected_address)
            lastAddressField.set("$selectedAddress\n${currentPickUpAddress!!.getAddressString()}")
            return
        }

        viewModelScope.launch {
            dataStoreHelper.selectedPickupAddress.collect { address ->
                if (address.street.isEmpty()) {
                    val addAddress =
                        resourcesProvider.getString(R.string.msg_creation_order_add_address)
                    lastAddressField.set(addAddress)
                } else {
                    currentPickUpAddress = address
                    val selectedAddress =
                        resourcesProvider.getString(R.string.msg_creation_order_selected_address)
                    lastAddressField.set("$selectedAddress\n${address.getAddressString()}")
                }
            }
        }
    }

    fun createOrder(orderEntity: OrderEntity) {
        viewModelScope.launch {
            if (isDeliveryField.get() == true) {
                if (currentDeliveryAddress != null) {
                    orderEntity.address = currentDeliveryAddress!!
                } else {
                    errorMessageLiveData.value =
                        resourcesProvider.getString(R.string.error_creation_order_address)
                    return@launch
                }
            } else {
                if (currentPickUpAddress != null) {
                    orderEntity.address = currentPickUpAddress!!
                } else {
                    errorMessageLiveData.value =
                        resourcesProvider.getString(R.string.error_creation_order_address)
                    return@launch
                }
            }
            orderEntity.code = generateCode()
            val order = Order(orderEntity, cartProductRepo.getCartProductListAsync().await())

            orderRepo.saveOrder(order)
            navigator?.get()?.goToMain(orderEntity)

            /*for (cartProduct in orderWithCartProducts.cartProducts) {
                cartProduct.orderUuid = insertedOrder.uuid
                cartProductRepo.insertAsync(cartProduct)
            }

            navigator?.get()?.goToMain(order)*/
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