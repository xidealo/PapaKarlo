package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.cafe.CafeRepo
import com.bunbeauty.domain.field_helper.IFieldHelper
import com.bunbeauty.domain.network.INetworkHelper
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.address.UserAddressRepo
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.CreationOrderFragmentDirections.toAddressesBottomSheet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

abstract class CreationOrderViewModel : ToolbarViewModel() {
    abstract val hasAddressState: StateFlow<State<Boolean>>
    abstract val selectedAddressState: StateFlow<State<String>>

    abstract val isDeliveryState: StateFlow<Boolean>

    abstract fun getAddress()
    abstract fun onAddressClicked()
    abstract fun onCreateAddressClicked()
}

class CreationOrderViewModelImpl @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val networkHelper: INetworkHelper,
    private val resourcesProvider: IResourcesProvider,
    private val stringHelper: IStringHelper,
    private val orderRepo: OrderRepo,
    private val cafeAddressRepo: CafeAddressRepo,
    private val userAddressRepo: UserAddressRepo,
    private val cafeRepo: CafeRepo,
    val iFieldHelper: IFieldHelper
) : CreationOrderViewModel() {

    val userId by lazy {
        runBlocking {
            dataStoreHelper.userId.first()
        }
    }

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    override val selectedAddressState: MutableStateFlow<State<String>> =
        MutableStateFlow(State.Loading())

    override val isDeliveryState: MutableStateFlow<Boolean> = MutableStateFlow(true)

    var deferredHoursLiveData = MutableLiveData<Int?>(null)
    var deferredMinutesLiveData = MutableLiveData<Int?>(null)

    override fun getAddress() {
        viewModelScope.launch(Dispatchers.Default) {
            if (isDeliveryState.value) {
                userAddressRepo.getUserAddressByUserId(userId).onEach {
                    hasAddressState.value = it.isNotEmpty().toStateSuccess()
                    if (it.isNotEmpty())
                        selectedAddressState.value =
                            stringHelper.toString(it.last()).toStateSuccess()
                }.launchIn(viewModelScope)
            } else {
                cafeAddressRepo.getCafeAddressByUuid(dataStoreHelper.cafeAddressId.first()).onEach {
                    if (it != null)
                        selectedAddressState.value = stringHelper.toString(it).toStateSuccess()
                    else
                        selectedAddressState.value =
                            resourcesProvider.getString(R.string.msg_creation_order_add_address)
                                .toStateSuccess()
                }.launchIn(viewModelScope)
            }
        }
    }

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
        if (selectedAddressState.value == null) {
            showError(resourcesProvider.getString(R.string.error_creation_order_address))
            return
        }

        val orderEntity = OrderEntity(
            comment = comment,
            phone = phone,
            email = email,
            deferred = stringHelper.toStringTime(deferredHours, deferredMinutes),
            isDelivery = isDeliveryState.value,
            code = generateCode(),
            cafeAddress = selectedAddressState.value
        )

        viewModelScope.launch(IO) {
            val order = Order(
                orderEntity,
                cartProductRepo.getCartProductList(),
                cafeRepo.getCafeEntityByDistrict(
                    orderEntity.cafeAddress.street?.districtId ?: "ERROR CAFE"
                ).id
            )

            /*
             dataStoreHelper.savePhoneNumber(orderEntity.phone)
              if (orderEntity.email.isNotEmpty()) {
                  dataStoreHelper.saveEmail(orderEntity.email)
              }*/

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

    fun isNetworkConnected(): Boolean {
        return networkHelper.isNetworkConnected()
    }

    override fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(isDeliveryState.value))
    }

    override fun onCreateAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    companion object {
        private const val CODE_NUMBER_COUNT = 100 // 0 - 99
        private const val MINUTES_IN_HOURS = 60
        private const val HALF_HOUR = 30
    }
}