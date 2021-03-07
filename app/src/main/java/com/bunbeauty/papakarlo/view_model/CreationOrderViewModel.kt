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
import com.bunbeauty.papakarlo.utils.network.INetworkHelper
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.utils.string.IStringHelper
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
    private val networkHelper: INetworkHelper,
    private val resourcesProvider: IResourcesProvider,
    private val stringHelper: IStringHelper,
    private val orderRepo: OrderRepo,
    private val addressRepo: AddressRepo,
    private val cafeRepo: CafeRepo
) : BaseViewModel() {

    var navigator: WeakReference<CreationOrderNavigator>? = null

    val errorMessageLiveData = MutableLiveData<String>()
    val hasAddressField = ObservableField(true)

    var deferredHoursLiveData = MutableLiveData<Int?>(null)
    var deferredMinutesLiveData = MutableLiveData<Int?>(null)

    val isDeliveryLiveData = MutableLiveData(true)
    val deferredTextLiveData = switchMap(isDeliveryLiveData) { isDelivery ->
        switchMap(deferredHoursLiveData) { deferredHours ->
            map(deferredMinutesLiveData) { deferredMinutes ->
                if (isDelivery) {
                    resourcesProvider.getString(R.string.action_creation_order_delivery_time) + stringHelper.toStringTime(
                        deferredHours,
                        deferredMinutes
                    )
                } else {
                    resourcesProvider.getString(R.string.action_creation_order_pickup_time) + stringHelper.toStringTime(
                        deferredHours,
                        deferredMinutes
                    )
                }
            }
        }
    }
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

    val totalCartPriceLiveData by lazy { MutableLiveData<String>() }

    fun getCartProductsCost() {
        viewModelScope.launch(IO) {
            val cartProductsCost = cartProductRepo.getCartProductList().sumBy { getFullPrice(it) }
            totalCartPriceLiveData.postValue("$cartProductsCost ₽")
        }
    }

    fun isDeferredTimeCorrect(deferredHours: Int, deferredMinutes: Int): Boolean {
        val limitMinutes = DateTime.now().minuteOfDay + HALF_HOUR
        val pickedMinutes = deferredHours * MINUTES_IN_HOURS + deferredMinutes

        return pickedMinutes > limitMinutes
    }

    private fun getFullPrice(cartProduct: CartProduct): Int {
        return cartProduct.menuProduct.cost * cartProduct.count
    }

    fun createOrder(
        comment: String,
        phone: String,
        email: String,
        deferredHours: Int?,
        deferredMinutes: Int?
    ) {
        val orderEntity = OrderEntity(
            comment = comment,
            phone = phone,
            email = email,
            deferred = stringHelper.toStringTime(deferredHours, deferredMinutes)
        )

        viewModelScope.launch(IO) {
            if (addressLiveData.value == null) {
                withContext(Main) {
                    errorMessageLiveData.value =
                        resourcesProvider.getString(R.string.error_creation_order_address)
                }
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
        val letters = resourcesProvider.getString(R.string.code_letters)

        val number = (DateTime.now().secondOfDay % (letters.length * CODE_NUMBER_COUNT))
        val codeLetter = letters[number % letters.length].toString()
        val codeNumber = (number / letters.length).toString()

        return codeLetter + codeNumber
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

    fun isNetworkConnected(): Boolean {
        return networkHelper.isNetworkConnected()
    }

    fun onOrderClick() {
        navigator?.get()?.createDeliveryOrder()
    }

    fun onAddAddressClick() {
        navigator?.get()?.goToCreationAddress()
    }

    companion object {
        private const val CODE_NUMBER_COUNT = 100 // 0 - 99
        private const val MINUTES_IN_HOURS = 60
        private const val HALF_HOUR = 30
    }
}