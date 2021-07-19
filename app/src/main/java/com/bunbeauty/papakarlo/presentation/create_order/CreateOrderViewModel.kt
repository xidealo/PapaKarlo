package com.bunbeauty.papakarlo.presentation.create_order

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.BONUSES_PERCENT
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.model.local.order.OrderEntity
import com.bunbeauty.domain.model.entity.UserEntity
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.network.INetworkHelper
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetDirections.toCreationAddressFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.CreateOrderFragmentDirections.*
import com.instacart.library.truetime.TrueTime
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.math.roundToInt

//abstract class CreateOrderViewModel : BaseViewModel() {
//    abstract val hasAddressState: StateFlow<State<Boolean>>
//    abstract val selectedAddressTextState: StateFlow<State<String>>
//    abstract val userState: StateFlow<State<User?>>
//
//    abstract val isDeliveryState: MutableStateFlow<Boolean>
//    abstract val deferredTextStateFlow: StateFlow<String>
//    abstract val orderStringStateFlow: StateFlow<String>
//    abstract val deferredHoursStateFlow: MutableStateFlow<Int?>
//    abstract val deferredMinutesStateFlow: MutableStateFlow<Int?>
//
//    abstract val deliveryStringLiveData: LiveData<String>
//
//    abstract fun getAddress()
//    abstract fun getUser()
//    abstract fun getCachedPhone(): String
//    abstract fun getCachedEmail(): String
//    abstract fun subscribeOnDeferredText()
//    abstract fun subscribeOnOrderString()
//    abstract fun isDeferredTimeCorrect(deferredHours: Int, deferredMinutes: Int): Boolean
//    abstract fun onAddressClicked()
//    abstract fun onCreateAddressClicked()
//    abstract fun createOrder(
//        comment: String,
//        phone: String,
//        email: String,
//        deferredHours: Int?,
//        deferredMinutes: Int?,
//        spentBonusesString: String
//    )
//}

class CreateOrderViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val networkHelper: INetworkHelper,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val resourcesProvider: IResourcesProvider,
    private val cartProductRepo: CartProductRepo,
    private val orderRepo: OrderRepo,
    private val cafeAddressRepo: CafeAddressRepo,
    private val userAddressRepo: UserAddressRepo,
    private val cafeRepo: CafeRepo,
    private val userRepo: UserRepo
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private val mutablePhone: MutableStateFlow<String?> = MutableStateFlow(null)
    val phone: StateFlow<String?> = mutablePhone.asStateFlow()

    init {
        subscribeOnProfile()
    }

    private fun subscribeOnProfile() {
        dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.getUser(userUuid).onEach { user ->
                mutablePhone.value = user?.phone
            }
        }.launchIn(viewModelScope)
    }


    val hasAddressState: MutableStateFlow<State<Boolean>> =
        MutableStateFlow(State.Loading())

    val selectedAddressTextState: MutableStateFlow<State<String>> =
        MutableStateFlow(State.Loading())

    val userEntityState: MutableStateFlow<State<UserEntity?>> =
        MutableStateFlow(State.Loading())

    private val selectedAddressState: MutableStateFlow<Address?> = MutableStateFlow(null)
    val deferredTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    val orderStringStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    val deferredHoursStateFlow = MutableStateFlow<Int?>(null)
    val deferredMinutesStateFlow = MutableStateFlow<Int?>(null)

    fun setIsDelivery(isDelivery: Boolean) {
        mutableIsDelivery.value = isDelivery
    }

    @ExperimentalCoroutinesApi
    fun getAddress() {
        viewModelScope.launch(Dispatchers.Default) {
            isDelivery.flatMapLatest { isDelivery ->
                if (isDelivery) {
                    dataStoreRepo.deliveryAddressId.flatMapLatest {
                        userAddressRepo.getUserAddressByUuid(it)
                    }
                } else {
                    dataStoreRepo.cafeAddressId.flatMapLatest {
                        cafeAddressRepo.getCafeAddressByCafeId(it)
                    }
                }
            }.onEach { address ->
                hasAddressState.value = (address != null).toStateSuccess()
                if (address != null) {
                    selectedAddressTextState.value =
                        stringUtil.toString(address).toStateSuccess()
                    selectedAddressState.value = address
                } else {
                    selectedAddressTextState.value =
                        resourcesProvider.getString(R.string.msg_creation_order_add_address)
                            .toStateSuccess()
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.Default) {
            userRepo.getUserWithBonuses(dataStoreRepo.userUuid.first()).onEach {
                userEntityState.value = it.toStateNullableSuccess()
            }.launchIn(viewModelScope)
        }
    }

    fun getCachedEmail(): String {
        return runBlocking { dataStoreRepo.email.first() }
    }

    fun getCachedPhone(): String {
        return runBlocking { dataStoreRepo.phone.first() }
    }

    @ExperimentalCoroutinesApi
    fun subscribeOnDeferredText() {
        isDelivery.flatMapLatest {
            deferredHoursStateFlow
        }.flatMapLatest { deferredMinutesStateFlow }.onEach {
            deferredTextStateFlow.value = if (isDelivery.value) {
                resourcesProvider.getString(R.string.action_creation_order_delivery_time) + stringUtil.toStringTime(
                    deferredHoursStateFlow.value,
                    deferredMinutesStateFlow.value
                )
            } else {
                resourcesProvider.getString(R.string.action_creation_order_pickup_time) + stringUtil.toStringTime(
                    deferredHoursStateFlow.value,
                    deferredMinutesStateFlow.value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun subscribeOnOrderString() {
        viewModelScope.launch(Dispatchers.Default) {
            cartProductRepo.cartProductList.onEach { productList ->
                if (isDelivery.value) {
                    orderStringStateFlow.value =
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceStringWithDelivery(
                                    productList,
                                    dataStoreRepo.delivery.first()
                                )
                } else {
                    orderStringStateFlow.value =
                        resourcesProvider.getString(R.string.action_creation_order_checkout) +
                                productHelper.getFullPriceString(productList)
                }
            }.launchIn(viewModelScope)
        }
    }

    val deliveryStringLiveData by lazy {
        switchMap(dataStoreRepo.delivery.asLiveData()) { delivery ->
            map(cartProductRepo.cartProductList.asLiveData()) { productList ->
                val differenceString = productHelper.getDifferenceBeforeFreeDeliveryString(
                    productList,
                    delivery.forFree
                )
                if (differenceString.isEmpty()) {
                    resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery)
                } else {
                    resourcesProvider.getString(R.string.part_creation_order_delivery_cost) +
                            stringUtil.getCostString(delivery.cost) +
                            resourcesProvider.getString(R.string.part_creation_order_free_delivery_from) +
                            stringUtil.getCostString(delivery.forFree)
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
        deferredMinutes: Int?,
        spentBonusesString: String
    ) {
        if (selectedAddressState.value == null) {
            showError(resourcesProvider.getString(R.string.error_creation_order_address))
            return
        }
        val orderEntity = OrderEntity(
            comment = comment,
            phone = phone,
            email = email,
            deferredTime = stringUtil.toStringTime(deferredHours, deferredMinutes),
            isDelivery = isDelivery.value,
            code = generateCode(),
            address = selectedAddressState.value!!,
        )
        //set try to get time?
        val timestamp = try {
            TrueTime.now().time
        } catch (exception: Exception) {
            DateTime.now().millis
        }
        viewModelScope.launch {
            val order = Order(
                orderEntity,
                cartProductRepo.getCartProductList(),
                cafeRepo.getCafeEntityByDistrict(
                    orderEntity.address.street?.districtId ?: "ERROR CAFE"
                ).id,
                timestamp = timestamp
            )
            if (userEntityState.value is State.Success) {
                val user = (userEntityState.value as State.Success<UserEntity?>).data
                //with login
                if (user != null) {
                    val spentBonuses = if (spentBonusesString.isEmpty()) {
                        0
                    } else {
                        spentBonusesString.toInt()
                    }
                    if (user.bonusList.sum() - spentBonuses < 0) {
                        showError("Недостаточно бонусов")
                        return@launch
                    } else {
                        if (spentBonuses != 0)
                            user.bonusList.add(-spentBonuses)
                    }
                    user.bonusList.add((productHelper.getNewTotalCost(order.cartProducts) * BONUSES_PERCENT).roundToInt())
                    order.orderEntity.bonus = spentBonuses
                    order.orderEntity.userId = user.uuid
                    userRepo.insertToBonusList(user)
                } else {
                    dataStoreRepo.savePhone(phone)
                    dataStoreRepo.saveEmail(email)
                }
                orderRepo.insert(order)
            }
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

    fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(isDelivery.value))
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