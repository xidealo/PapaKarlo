package com.bunbeauty.papakarlo.presentation.create_order

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.ASAP
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.CREATE_ORDER_TAG
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.Order
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.code.ICodeGenerator
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragmentDirections.*
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOrderViewModel @Inject constructor(
    @Api private val cartProductRepo: CartProductRepo,
    @Firebase private val userAddressRepo: UserAddressRepo,
    @Api private val cafeRepo: CafeRepo,
    @Firebase private val userRepo: UserRepo,
    @Firebase private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val resourcesProvider: IResourcesProvider,
    private val codeGenerator: ICodeGenerator,
    private val orderUtil: IOrderUtil,
    private val authUtil: IAuthUtil,
    private val dateTimeUtils: IDateTimeUtil,
    private val cartProductMapper: com.example.domain_firebase.mapper.ICartProductMapper
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private var userUuidValue: String? = null
    private val mutablePhone: MutableStateFlow<String?> = MutableStateFlow(null)
    val phone: StateFlow<String?> = mutablePhone.asStateFlow()

    private var userAddress: UserAddress? = null
    private var cafeAddress: String? = null
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

    private var deferredTimeValue: String? = null
    private val mutableDeferredTime: MutableStateFlow<String?> = MutableStateFlow(null)
    val deferredTime: StateFlow<String?> = mutableDeferredTime.asStateFlow()

    private val cartProductList: MutableList<CartProduct> = mutableListOf()
    private val mutableTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val totalCost: StateFlow<String> = mutableTotalCost.asStateFlow()

    private val mutableDeliveryCost: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryCost: StateFlow<String> = mutableDeliveryCost.asStateFlow()

    private val mutableNewAmountToPay: MutableStateFlow<String> = MutableStateFlow("")
    val newAmountToPay: StateFlow<String> = mutableNewAmountToPay.asStateFlow()

    init {
        subscribeOnAddress()
        subscribeOnDeferredTime()
        subscribeOnCartProduct()
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
        deferredTimeValue = deferredTime
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

        val isDelivery = mutableIsDelivery.value
        if ((isDelivery && (userAddress == null)) || (!isDelivery && (cafeAddress == null))) {
            showError(resourcesProvider.getString(R.string.error_create_order_address))
            return
        }

        if (deferredTimeValue == null) {
            showError(resourcesProvider.getString(R.string.error_create_order_time))
            return
        }

        viewModelScope.launch {
            val deferredTime = if (deferredTimeValue == ASAP) {
                null
            } else {
                mutableDeferredTime.value
            }
            val currentMillis = dateTimeUtils.currentTimeMillis
            val letters = resourcesProvider.getString(R.string.code_letters)
            val code = codeGenerator.generateCode(currentMillis, letters)
            val orderProductList = cartProductList.map(cartProductMapper::toOrderProduct)
            val cafeUuid = if (isDelivery) {
                cafeRepo.getCafeByStreetUuid(userAddress!!.streetUuid).uuid
            } else {
                //cafeAddress!!.cafeUuid
            }
            val order = Order(
                isDelivery = isDelivery,
                userUuid = checkNotNull(userUuidValue),
                phone = checkNotNull(mutablePhone.value),
                userAddress = userAddress,
                cafeAddress = cafeAddress,
                comment = mutableComment.value,
                deferredTime = deferredTime,
                time = currentMillis,
                code = code,
                orderStatus = OrderStatus.NOT_ACCEPTED,
                orderProductList = orderProductList,
                cafeUuid = ""
            )
            cartProductRepo.deleteCartProductList(cartProductList)
            orderRepo.saveOrder(order)

            showMessage(stringUtil.getCodeString(code))
            goBack()
        }
    }

    fun setUser() {
        viewModelScope.launch {
            val userUuid = authUtil.userUuid
            if (authUtil.isAuthorize && userUuid != null) {
                val user = userRepo.getUserByUuid(userUuid)
                mutablePhone.value = user?.phone
                userUuidValue = user?.uuid
            }
        }
    }

    private fun subscribeOnAddress() {
        isDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                dataStoreRepo.userUuid.flatMapLatest { userUuid ->
                    Log.d(
                        CREATE_ORDER_TAG,
                        "CreateOrderViewModel -> subscribeOnAddress; userUuid = $userUuid"
                    )
                    if (userUuid == null) {
                        userAddressRepo.observeUnassignedUserAddressList().onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                userAddress = addressList.first()
                                mutableAddress.value =
                                    stringUtil.getUserAddressString(addressList.first())
                            }
                        }
                    } else {
                        dataStoreRepo.userAddressUuid.flatMapLatest { userAddressUuid ->
                            Log.d(
                                CREATE_ORDER_TAG,
                                "CreateOrderViewModel -> subscribeOnAddress; userAddressUuid = $userAddressUuid"
                            )
                            if (userAddressUuid == null) {
                                userAddressRepo.observeUserAddressListByUserUuid(userUuid)
                                    .onEach { addressList ->
                                        if (addressList.isNotEmpty()) {
                                            userAddress = addressList.first()
                                            mutableAddress.value =
                                                stringUtil.getUserAddressString(addressList.first())
                                        } else {
                                            userAddress = null
                                            mutableAddress.value = null
                                        }
                                        Log.d(
                                            CREATE_ORDER_TAG,
                                            "CreateOrderViewModel -> subscribeOnAddress; addressList = " +
                                                    addressList.size
                                        )
                                    }
                            } else {
                                userAddressRepo.observeUserAddressByUuid(userAddressUuid)
                                    .onEach { address ->
                                        userAddress = address
                                        mutableAddress.value =
                                            stringUtil.getUserAddressString(address)
                                    }
                            }
                        }
                    }
                }
            } else {
                dataStoreRepo.cafeAddressUuid.flatMapLatest { cafeAddressUuid ->
                    if (cafeAddressUuid == null) {
                        cafeRepo.observeCafeAddressList().onEach { addressList ->
                            if (addressList.isNotEmpty()) {
                                //cafeAddress = addressList.first()
                                mutableAddress.value =
                                    stringUtil.getCafeAddressString(addressList.first())
                            } else {
                                cafeAddress = null
                                mutableAddress.value = ""
                            }
                        }
                    } else {
                        cafeRepo.observeCafeAddressByUuid(cafeAddressUuid).onEach { address ->
                            //cafeAddress = address
                            mutableAddress.value = stringUtil.getCafeAddressString(address)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnDeferredTime() {
        dataStoreRepo.deferredTime.onEach { deferredTime ->
            deferredTimeValue = deferredTime
            mutableDeferredTime.value = mapDeferredTime(deferredTime)
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnCartProduct() {
        cartProductRepo.observeCartProductList().flatMapLatest { productList ->
            cartProductList.clear()
            cartProductList.addAll(productList)
            val totalCost = productHelper.getNewTotalCost(productList)
            mutableTotalCost.value = stringUtil.getCostString(totalCost)

            mutableIsDelivery.onEach { isDelivery ->
                val delivery = dataStoreRepo.delivery.first()
                val deliveryCost = orderUtil.getDeliveryCost(isDelivery, productList, delivery)
                mutableDeliveryCost.value = stringUtil.getCostString(deliveryCost)

                val amountToPay = orderUtil.getNewOrderCost(isDelivery, productList, delivery)
                mutableNewAmountToPay.value = stringUtil.getCostString(amountToPay)
            }
        }.launchIn(viewModelScope)
    }

    private fun mapDeferredTime(deferredTime: String?): String? {
        return if (deferredTime == ASAP) {
            resourcesProvider.getString(R.string.msg_deferred_time_asap)
        } else {
            deferredTime
        }
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