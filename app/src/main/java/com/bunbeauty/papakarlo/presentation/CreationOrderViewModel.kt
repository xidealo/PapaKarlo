package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.model.Address
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.domain.network.INetworkHelper
import com.bunbeauty.domain.repository.address.AddressRepo
import com.bunbeauty.domain.repository.cafe.CafeRepo
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.CreationOrderFragmentDirections.toAddressesBottomSheet
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val networkHelper: INetworkHelper,
    private val resourcesProvider: IResourcesProvider,
    private val stringHelper: IStringHelper,
    private val orderRepo: OrderRepo,
    private val addressRepo: AddressRepo,
    private val cafeRepo: CafeRepo
) : ToolbarViewModel() {

    val hasAddressLiveData = MutableLiveData(false)

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
                    hasAddressLiveData.value = deliveryAddress != null
                    deliveryAddress
                }
            }
        }
    }

    private val pickupAddressLiveData: LiveData<Address?> by lazy {
        switchMap(dataStoreHelper.cafeId.asLiveData()) { addressId ->
            addressRepo.getAddressByCafeId(addressId).asLiveData()
        }
    }

    val addressLiveData: LiveData<Address?> by lazy {
        switchMap(isDeliveryLiveData) { isDelivery ->
            if (isDelivery) {
                hasAddressLiveData.value = deliveryAddressLiveData.value != null
                deliveryAddressLiveData
            } else {
                hasAddressLiveData.value = true
                pickupAddressLiveData
            }
        }
    }
    val deliveryStringLiveData by lazy {
        switchMap(dataStoreHelper.delivery.asLiveData()) { delivery ->
            map(cartProductRepo.getCartProductListLiveData()) { productList ->
                val differenceString = productHelper.getDifferenceBeforeFreeDeliveryString(
                    productList,
                    delivery.forFree
                )
                if (differenceString.isEmpty()) {
                    resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery)
                } else {
                    resourcesProvider.getString(R.string.part_creation_order_delivery_cost) +
                            stringHelper.toStringCost(delivery.cost) +
                            resourcesProvider.getString(R.string.part_creation_order_free_delivery_from) +
                            stringHelper.toStringCost(delivery.forFree)
                }
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

    val orderStringLiveData by lazy {
        switchMap(cartProductRepo.getCartProductListLiveData()) { productList ->
            switchMap(dataStoreHelper.delivery.asLiveData()) { delivery ->
                map(isDeliveryLiveData) { isDelivery ->
                    if (isDelivery) {
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceStringWithDelivery(productList, delivery)
                    } else {
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceString(productList)
                    }
                }
            }
        }
    }

    fun isDeferredTimeCorrect(deferredHours: Int, deferredMinutes: Int): Boolean {
        val limitMinutes = DateTime.now().minuteOfDay + HALF_HOUR
        val pickedMinutes = deferredHours * MINUTES_IN_HOURS + deferredMinutes

        return pickedMinutes > limitMinutes
    }

    fun createOrder(
        comment: String,
        phone: String,
        email: String,
        deferredHours: Int?,
        deferredMinutes: Int?
    ) {
        if (addressLiveData.value == null) {
            showError(resourcesProvider.getString(R.string.error_creation_order_address))
            return
        }

        val orderEntity = OrderEntity(
            comment = comment,
            phone = phone,
            email = email,
            deferred = stringHelper.toStringTime(deferredHours, deferredMinutes),
            isDelivery = isDeliveryLiveData.value!!,
            code = generateCode(),
            address = addressLiveData.value!!
        )
        viewModelScope.launch(IO) {
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
                showMessage(resourcesProvider.getString(R.string.msg_creation_order_order_code) + orderEntity.code)
                router.navigate(backToMenuFragment())
            }
        }
    }

    private fun generateCode(): String {
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

    fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(isDeliveryLiveData.value!!))
    }

    fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    companion object {
        private const val CODE_NUMBER_COUNT = 100 // 0 - 99
        private const val MINUTES_IN_HOURS = 60
        private const val HALF_HOUR = 30
    }
}