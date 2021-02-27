package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderEntity
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val resourcesProvider: IResourcesProvider,
    private val orderRepo: OrderRepo,
    private val addressRepo: AddressRepo,
    private val cafeRepo: CafeRepo
) : BaseViewModel() {

    var navigator: WeakReference<CreationOrderNavigator>? = null

    val errorMessageLiveData = MutableLiveData<String>()
    val hasAddressField = ObservableField(true)

    val isDeliveryLiveData = MutableLiveData(true)
    private val deliveryAddressLiveData: LiveData<Address?> by lazy {
        switchMap(dataStoreHelper.deliveryAddressId.asLiveData()) { addressId ->
            switchMap(addressRepo.getAddressById(addressId)) { address ->
                map(addressRepo.getAddressById(addressId)) { firstAddress ->
                    val deliveryAddress = address ?: firstAddress
                    hasAddressField.set(deliveryAddress != null)
                    deliveryAddress
                }
            }
        }
    }
    private val pickupAddressLiveData: LiveData<Address?> by lazy {
        switchMap(dataStoreHelper.cafeId.asLiveData()) { addressId ->
            addressRepo.getAddressByCafeId(addressId)
        }
    }
    val addressLiveData: LiveData<Address?> by lazy {
        switchMap(isDeliveryLiveData) { isDelivery ->
            if (isDelivery) {
                hasAddressField.set(deliveryAddressLiveData.value != null)
                deliveryAddressLiveData
            } else {
                hasAddressField.set(true)
                pickupAddressLiveData
            }
        }
    }
    val phoneNumber by lazy {
        runBlocking {
            dataStoreHelper.phoneNumber.first()
        }
    }
    val email by lazy {
        runBlocking {
            dataStoreHelper.email.first()
        }
    }

    val cartLiveData by lazy {
        map(cartProductListLiveData) { productList ->
            "${productList.sumBy { getFullPrice(it) }} ₽"
        }
    }

    private fun getFullPrice(cartProduct: CartProduct): Int {
        return cartProduct.menuProduct.cost * cartProduct.count
    }

    fun createOrder(orderEntity: OrderEntity) {
        viewModelScope.launch(IO) {
            if (addressLiveData.value == null) {
                errorMessageLiveData.value =
                    resourcesProvider.getString(R.string.error_creation_order_address)
                return@launch
            } else {
                orderEntity.address = addressLiveData.value!!
            }

            orderEntity.isDelivery = isDeliveryLiveData.value!!
            orderEntity.code = generateCode()
            val order = Order(
                orderEntity,
                cartProductRepo.getCartProductList(),
                cafeRepo.getCafeEntityByDistrict(
                    orderEntity.address.street?.districtId ?: "ERROR CAFE"
                ).id
            )

            dataStoreHelper.savePhoneNumber(orderEntity.phone)
            if (orderEntity.email.isNotEmpty()) {
                dataStoreHelper.saveEmail(orderEntity.email)
            }
            orderRepo.saveOrder(order)

            withContext(Main) {
                navigator?.get()?.goToMain(orderEntity)
            }
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