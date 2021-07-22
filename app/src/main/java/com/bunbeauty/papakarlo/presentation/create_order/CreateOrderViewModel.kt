package com.bunbeauty.papakarlo.presentation.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.ASAP
import com.bunbeauty.common.Constants.CODE_DIVIDER
import com.bunbeauty.common.Constants.CODE_NUMBER_COUNT
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.ui.address.Address
import com.bunbeauty.domain.model.ui.order.OrderUI
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.network.INetworkHelper
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragmentDirections.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    private val orderUtil: IOrderUtil,
    private val userRepo: UserRepo,
    private val dateTimeUtils: IDateTimeUtil,
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private var userUuid: String? = null
    private val mutablePhone: MutableStateFlow<String?> = MutableStateFlow(null)
    val phone: StateFlow<String?> = mutablePhone.asStateFlow()

    private var addressModel: Address? = null
    private val mutableAddress: MutableStateFlow<String?> = MutableStateFlow(null)
    val address: StateFlow<String?> = mutableAddress.asStateFlow()

    private val mutableComment: MutableStateFlow<String?> = MutableStateFlow(null)
    val comment: StateFlow<String?> = mutableComment.asStateFlow()

    private val actionDeliveryTime =
        resourcesProvider.getString(R.string.action_create_order_delivery_time)
    private val actionPickupTime =
        resourcesProvider.getString(R.string.action_create_order_pickup_time)
    private val mutableActionDeferredTime: MutableStateFlow<String> =
        MutableStateFlow(actionDeliveryTime)
    val actionDeferredTime: StateFlow<String> = mutableActionDeferredTime.asStateFlow()

    private val hintDeliveryTime =
        resourcesProvider.getString(R.string.hint_create_order_delivery_time)
    private val hintPickupTime =
        resourcesProvider.getString(R.string.hint_create_order_pickup_time)
    private val mutableHintDeferredTime: MutableStateFlow<String> =
        MutableStateFlow(hintDeliveryTime)
    val hintDeferredTime: StateFlow<String> = mutableHintDeferredTime.asStateFlow()

    private val mutableDeferredTime: MutableStateFlow<String?> = MutableStateFlow(null)
    val deferredTime: StateFlow<String?> = mutableDeferredTime.asStateFlow()

    private val mutableTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val totalCost: StateFlow<String> = mutableTotalCost.asStateFlow()

    private val mutableDeliveryCost: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryCost: StateFlow<String> = mutableDeliveryCost.asStateFlow()

    private val mutableNewAmountToPay: MutableStateFlow<String> = MutableStateFlow("")
    val newAmountToPay: StateFlow<String> = mutableNewAmountToPay.asStateFlow()

    init {
        subscribeOnUser()
        subscribeOnAddress()
        subscribeOnDeferredTime()
        subscribeOnCartProduct()
    }

    private fun subscribeOnUser() {
        dataStoreRepo.userUuid.flatMapLatest { userUuid ->
            userRepo.getUserByUuid(userUuid).onEach { user ->
                mutablePhone.value = user?.phone
                this.userUuid = user?.uuid
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnAddress() {
        isDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                dataStoreRepo.userUuid.flatMapLatest { userUuid ->
                    if (userUuid == null) {
                        userAddressRepo.unassignedUserAddressList.onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                addressModel = addressList.first()
                                mutableAddress.value = stringUtil.toString(addressList.first())
                            }
                        }
                    } else {
                        dataStoreRepo.userAddressUuid.flatMapLatest { userAddressUuid ->
                            if (userAddressUuid == null) {
                                userAddressRepo.getUserAddressListByUserUuid(userUuid)
                                    .onEach { addressList ->
                                        if (addressList.isNotEmpty()) {
                                            addressModel = addressList.first()
                                            mutableAddress.value =
                                                stringUtil.toString(addressList.first())
                                        }
                                    }
                            } else {
                                userAddressRepo.getUserAddressByUuid(userAddressUuid)
                                    .onEach { userAddress ->
                                        if (userAddress != null) {
                                            addressModel = userAddress
                                            mutableAddress.value = stringUtil.toString(userAddress)
                                        }
                                    }
                            }
                        }
                    }
                }
            } else {
                dataStoreRepo.cafeAddressUuid.flatMapLatest { cafeAddressUuid ->
                    if (cafeAddressUuid == null) {
                        cafeAddressRepo.getCafeAddresses().onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                addressModel = addressList.first()
                                mutableAddress.value = stringUtil.toString()
                            } else {
                                mutableAddress.value = ""
                            }
                        }
                    } else {
                        cafeAddressRepo.getCafeAddressByUuid(cafeAddressUuid)
                            .onEach { cafeAddress ->
                                addressModel = cafeAddress
                                mutableAddress.value = stringUtil.toString(cafeAddress)
                            }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnDeferredTime() {
        dataStoreRepo.deferredTime.onEach { deferredTime ->
            mutableDeferredTime.value = mapDeferredTime(deferredTime)
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnCartProduct() {
        cartProductRepo.cartProductList.flatMapLatest { productList ->
            mutableIsDelivery.onEach { isDelivery ->
                val totalCost = productHelper.getNewTotalCost(productList)
                mutableTotalCost.value = stringUtil.getCostString(totalCost)

                val delivery = dataStoreRepo.delivery.first()
                val deliveryCost = orderUtil.getDeliveryCost(isDelivery, productList, delivery)
                mutableDeliveryCost.value = stringUtil.getCostString(deliveryCost)

                val amountToPay = orderUtil.getNewOrderCost(isDelivery, productList, delivery)
                mutableNewAmountToPay.value = stringUtil.getCostString(amountToPay)
            }
        }.launchIn(viewModelScope)
    }

    fun onIsDeliveryChanged(isDelivery: Boolean) {
        mutableIsDelivery.value = isDelivery
        if (isDelivery) {
            mutableActionDeferredTime.value = actionDeliveryTime
            mutableHintDeferredTime.value = hintDeliveryTime
        } else {
            mutableActionDeferredTime.value = actionPickupTime
            mutableHintDeferredTime.value = hintPickupTime
        }
    }

    fun onDeferredTimeSelected(deferredTime: String) {
        mutableDeferredTime.value = mapDeferredTime(deferredTime)
        viewModelScope.launch {
            dataStoreRepo.saveDeferredTime(deferredTime)
        }
    }

    fun onCommentChanged(newComment: String) {
        if (newComment.isEmpty()) {
            mutableComment.value = null
        } else {
            mutableComment.value = newComment
        }
    }

    fun onCreateOrderClicked() {
        if (mutablePhone.value == null) {
            showError(resourcesProvider.getString(R.string.error_create_order_phone))
            return
        }

        if (mutableAddress.value.isNullOrEmpty()) {
            showError(resourcesProvider.getString(R.string.error_create_order_address))
            return
        }

        if (mutableDeferredTime.value == null) {
            showError(resourcesProvider.getString(R.string.error_create_order_time))
            return
        }

        val deferredTime = if (mutableDeferredTime.value == ASAP) {
            null
        } else {
            mutableDeferredTime.value
        }
        val currentMillis = dateTimeUtils.currentTimeMillis
        val code = generateCode(currentMillis)
        val order = OrderUI(
            isDelivery = mutableIsDelivery.value,
            userUuid = checkNotNull(userUuid),
            phone = checkNotNull(mutablePhone.value),
            address = checkNotNull(addressModel),
            comment = mutableComment.value,
            deferredTime = deferredTime,
            time = currentMillis,
            code = code,
            orderStatus = OrderStatus.NOT_ACCEPTED,
        )
        viewModelScope.launch {
            orderRepo.saveOrder(order)
            showMessage(stringUtil.getCodeString(code))
            goBack()
        }
    }

    private fun mapDeferredTime(deferredTime: String?): String? {
        return if (deferredTime == ASAP) {
            resourcesProvider.getString(R.string.msg_deferred_time_asap)
        } else {
            deferredTime
        }
    }

    private fun generateCode(currentMillis: Long): String {
        val currentSeconds = currentMillis / 1000
        val letters = resourcesProvider.getString(R.string.code_letters)
        val number = (currentSeconds % (letters.length * CODE_NUMBER_COUNT)).toInt()
        val codeLetter = letters[number % letters.length].toString()
        val codeNumber = (number / letters.length)
        val codeNumberString = if (codeNumber < 10) {
            "0$codeNumber"
        } else {
            codeNumber.toString()
        }

        return codeLetter + CODE_DIVIDER + codeNumberString
    }

    fun onAddressClicked() {
        router.navigate(toAddressesBottomSheet(isDelivery.value))
    }

    fun onAddAddressClicked() {
        router.navigate(toCreationAddressFragment())
    }

    fun onPhoneClicked() {
        router.navigate(toLoginFragment())
    }

    fun onDeferredTimeClicked() {
        router.navigate(toDeferredTimeBottomSheet(actionDeferredTime.value))
    }

    fun onAddCommentClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_create_order_addition_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = OneLineActionType.COMMENT,
            inputText = "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEditCommentClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_create_order_editing_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = OneLineActionType.COMMENT,
            inputText = comment.value ?: "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }
}