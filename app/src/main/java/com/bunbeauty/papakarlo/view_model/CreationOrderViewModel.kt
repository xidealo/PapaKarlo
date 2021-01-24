package com.bunbeauty.papakarlo.view_model

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val iDataStoreHelper: IDataStoreHelper,
    private val context: Context
) : BaseViewModel() {
    var navigator: WeakReference<CreationOrderNavigator>? = null

    val errorMessageLiveData = MutableLiveData<String>()
    val lastAddressField = ObservableField<String>()
    val isDeliveryField = ObservableField<Boolean>()
    var currentDeliveryAddress: Address? = null
    var currentPickUpAddress: Address? = null

    fun getLastDeliveryAddress() {
        if (currentDeliveryAddress != null) {
            lastAddressField.set("${context.getString(R.string.msg_creation_order_selected_address)}\n${currentDeliveryAddress!!.getAddressString()}")
            lastAddressField.notifyChange()
            return
        }

        viewModelScope.launch {
            iDataStoreHelper.selectedDeliveryAddress.collect {
                if (it.street.isEmpty()) {
                    lastAddressField.set(context.getString(R.string.msg_creation_order_add_address))
                } else {
                    currentDeliveryAddress = it
                    lastAddressField.set("${context.getString(R.string.msg_creation_order_selected_address)}\n${it.getAddressString()}")
                }
            }
        }
    }

    fun getLastPickupAddress() {
        if (currentPickUpAddress != null) {
            lastAddressField.set("${context.getString(R.string.msg_creation_order_selected_address)}\n${currentPickUpAddress!!.getAddressString()}")
            lastAddressField.notifyChange()
            return
        }

        viewModelScope.launch {
            iDataStoreHelper.selectedPickupAddress.collect {
                if (it.street.isEmpty()) {
                    lastAddressField.set(context.getString(R.string.msg_creation_order_add_address))
                } else {
                    currentPickUpAddress = it
                    lastAddressField.set("${context.getString(R.string.msg_creation_order_selected_address)}\n${it.getAddressString()}")
                }
            }
        }
    }

    fun createOrder(order: Order) {
        viewModelScope.launch {

            if (isDeliveryField.get() == true)
                if (currentDeliveryAddress != null) {
                    order.address = currentDeliveryAddress ?: Address(street = "ERROR ADDRESS")
                } else {
                    errorMessageLiveData.value =
                        context.getString(R.string.error_creation_order_address)
                    return@launch
                }
            else
                if (currentPickUpAddress != null) {
                    order.address = currentPickUpAddress ?: Address(street = "ERROR ADDRESS")
                } else {
                    errorMessageLiveData.value =
                        context.getString(R.string.error_creation_order_address)
                    return@launch
                }

            val orderWithCartProducts = OrderWithCartProducts(
                order,
                cartProductRepo.getCartProductListAsync().await()
            )

            val insertedOrder = orderRepo.insertOrderAsync(orderWithCartProducts.order).await()

            for (cartProduct in orderWithCartProducts.cartProducts) {
                cartProduct.orderUuid = insertedOrder.uuid
                cartProductRepo.insertAsync(cartProduct)
            }

            navigator?.get()?.goToMain(order)
        }
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

    fun createOrderClick() {
        navigator?.get()?.createDeliveryOrder()
    }

    fun createAddressClick() {
        navigator?.get()?.goToCreationAddress()
    }
}