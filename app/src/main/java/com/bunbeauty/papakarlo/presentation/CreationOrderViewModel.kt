package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants
import com.bunbeauty.common.Constants.BONUSES_PERCENT
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.data.model.address.Address
import com.bunbeauty.data.model.order.Order
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.model.user.User
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.cafe.CafeRepo
import com.bunbeauty.domain.network.INetworkHelper
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.address.UserAddressRepo
import com.bunbeauty.domain.repository.order.OrderRepo
import com.bunbeauty.domain.repository.user.UserRepo
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.CreationOrderFragmentDirections.toAddressesBottomSheet
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.math.roundToInt

abstract class CreationOrderViewModel : ToolbarViewModel() {
    abstract val hasAddressState: StateFlow<State<Boolean>>
    abstract val selectedAddressTextState: StateFlow<State<String>>
    abstract val userState: StateFlow<State<User?>>

    abstract val isDeliveryState: MutableStateFlow<Boolean>
    abstract val deferredTextStateFlow: StateFlow<String>
    abstract val orderStringStateFlow: StateFlow<String>
    abstract val deferredHoursStateFlow: MutableStateFlow<Int?>
    abstract val deferredMinutesStateFlow: MutableStateFlow<Int?>

    abstract val deliveryStringLiveData: LiveData<String>

    abstract fun getAddress()
    abstract fun getUser()
    abstract fun subscribeOnDeferredText()
    abstract fun subscribeOnOrderString()
    abstract fun isDeferredTimeCorrect(deferredHours: Int, deferredMinutes: Int): Boolean
    abstract fun onAddressClicked()
    abstract fun onCreateAddressClicked()
    abstract fun createOrder(
        comment: String,
        phone: String,
        email: String,
        deferredHours: Int?,
        deferredMinutes: Int?,
        spentBonusesString: String
    )
}

@ExperimentalCoroutinesApi
class CreationOrderViewModelImpl @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val networkHelper: INetworkHelper,
    private val resourcesProvider: IResourcesProvider,
    private val stringHelper: IStringHelper,
    private val orderRepo: OrderRepo,
    private val cafeAddressRepo: CafeAddressRepo,
    private val userAddressRepo: UserAddressRepo,
    private val cafeRepo: CafeRepo,
    private val userRepo: UserRepo
) : CreationOrderViewModel() {

    override val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    override val selectedAddressTextState: MutableStateFlow<State<String>> =
        MutableStateFlow(State.Loading())

    override val userState: MutableStateFlow<State<User?>> =
        MutableStateFlow(State.Loading())

    private val selectedAddressState: MutableStateFlow<Address?> = MutableStateFlow(null)

    override val isDeliveryState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override val deferredTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    override val orderStringStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val deferredHoursStateFlow = MutableStateFlow<Int?>(null)
    override val deferredMinutesStateFlow = MutableStateFlow<Int?>(null)

    override fun getAddress() {
        viewModelScope.launch(Dispatchers.Default) {
            isDeliveryState.flatMapLatest { isDelivery ->
                if (isDelivery) {
                    dataStoreHelper.deliveryAddressId.flatMapLatest {
                        userAddressRepo.getUserAddressByUuid(it)
                    }
                } else {
                    dataStoreHelper.cafeAddressId.flatMapLatest {
                        cafeAddressRepo.getCafeAddressByCafeId(it)
                    }
                }
            }.onEach { address ->
                hasAddressState.value = (address != null).toStateSuccess()
                if (address != null) {
                    selectedAddressTextState.value =
                        stringHelper.toString(address).toStateSuccess()
                    selectedAddressState.value = address
                } else {
                    selectedAddressTextState.value =
                        resourcesProvider.getString(R.string.msg_creation_order_add_address)
                            .toStateSuccess()
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun getUser() {
        viewModelScope.launch(Dispatchers.Default) {
            userRepo.getUserAsFlow(dataStoreHelper.userId.first()).onEach {
                userState.value = it.toStateNullableSuccess()
            }.launchIn(viewModelScope)
        }
    }

    override fun subscribeOnDeferredText() {
        isDeliveryState.flatMapLatest {
            deferredHoursStateFlow
        }.flatMapLatest { deferredMinutesStateFlow }.onEach {
            deferredTextStateFlow.value = if (isDeliveryState.value) {
                resourcesProvider.getString(R.string.action_creation_order_delivery_time) + stringHelper.toStringTime(
                    deferredHoursStateFlow.value,
                    deferredMinutesStateFlow.value
                )
            } else {
                resourcesProvider.getString(R.string.action_creation_order_pickup_time) + stringHelper.toStringTime(
                    deferredHoursStateFlow.value,
                    deferredMinutesStateFlow.value
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun subscribeOnOrderString() {
        viewModelScope.launch(Dispatchers.Default) {
            cartProductRepo.getCartProductListFlow().onEach { productList ->
                if (isDeliveryState.value) {
                    orderStringStateFlow.value =
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceStringWithDelivery(
                                    productList,
                                    dataStoreHelper.delivery.first()
                                )
                } else {
                    orderStringStateFlow.value =
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceString(productList)
                }
            }.launchIn(viewModelScope)
        }
    }

    override val deliveryStringLiveData by lazy {
        switchMap(dataStoreHelper.delivery.asLiveData()) { delivery ->
            map(cartProductRepo.getCartProductListFlow().asLiveData()) { productList ->
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

    override fun isDeferredTimeCorrect(deferredHours: Int, deferredMinutes: Int): Boolean {
        val limitMinutes = DateTime.now().minuteOfDay + HALF_HOUR
        val pickedMinutes = deferredHours * MINUTES_IN_HOURS + deferredMinutes

        return pickedMinutes > limitMinutes
    }

    override fun createOrder(
        comment: String,
        phone: String,
        email: String,
        deferredHours: Int?,
        deferredMinutes: Int?,
        spentBonusesString: String
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            if (selectedAddressState.value == null) {
                errorSharedFlow.emit(resourcesProvider.getString(R.string.error_creation_order_address))
                return@launch
            }
            val orderEntity = OrderEntity(
                comment = comment,
                phone = phone,
                email = email,
                deferred = stringHelper.toStringTime(deferredHours, deferredMinutes),
                isDelivery = isDeliveryState.value,
                code = generateCode(),
                address = selectedAddressState.value!!
            )
            val order = Order(
                orderEntity,
                cartProductRepo.getCartProductList(),
                cafeRepo.getCafeEntityByDistrict(
                    orderEntity.address.street?.districtId ?: "ERROR CAFE"
                ).id
            )
            if (userState.value is State.Success) {
                val user = (userState.value as State.Success<User?>).data!!
                val spentBonuses = if (spentBonusesString.isEmpty()) {
                    0
                } else {
                    spentBonusesString.toInt()
                }
                user.bonus += (productHelper.getFullPrice(order.cartProducts) * BONUSES_PERCENT).roundToInt()
                if (user.bonus - spentBonuses < 0) {
                    //show alert about bonuses
                    return@launch
                } else {
                    user.bonus -= spentBonuses
                }
                userRepo.update(user)
                orderRepo.insert(order)
                withContext(Main) {
                    messageSharedFlow.emit(resourcesProvider.getString(R.string.msg_creation_order_order_code) + orderEntity.code)
                    router.navigate(backToMenuFragment())
                }
            } else {
                //show alert about user not loaded
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