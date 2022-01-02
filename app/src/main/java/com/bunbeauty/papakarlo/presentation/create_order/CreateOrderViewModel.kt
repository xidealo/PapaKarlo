package com.bunbeauty.papakarlo.presentation.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.common.Logger.ORDER_TAG
import com.bunbeauty.common.Logger.logD
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.domain.model.order.OrderDetails
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.*
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.create_order.CreateOrderFragmentDirections.*
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateOrderViewModel @Inject constructor(
    @Api private val cartProductRepo: CartProductRepo,
    @Api private val userAddressRepo: UserAddressRepo,
    @Api private val cafeRepo: CafeRepo,
    @Api private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper,
    private val resourcesProvider: IResourcesProvider,
    private val orderUtil: IOrderUtil,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val dateTimeUtils: IDateTimeUtil,
) : BaseViewModel() {

    private val mutableIsDelivery: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isDelivery: StateFlow<Boolean> = mutableIsDelivery.asStateFlow()

    private var selectedUserAddressUuid: String? = null
    private var selectedCafeUuid: String? = null
    private val mutableAddress: MutableStateFlow<String?> = MutableStateFlow(null)
    val address: StateFlow<String?> = mutableAddress.asStateFlow()

    private val mutableComment: MutableStateFlow<String?> = MutableStateFlow(null)
    val comment: StateFlow<String?> = mutableComment.asStateFlow()

    private val deliveryTimeHint =
        resourcesProvider.getString(R.string.hint_create_order_delivery_time)
    private val pickupTimeHint =
        resourcesProvider.getString(R.string.hint_create_order_pickup_time)
    private val mutableDeferredTimeHint: MutableStateFlow<String> =
        MutableStateFlow(deliveryTimeHint)
    val deferredTimeHint: StateFlow<String> = mutableDeferredTimeHint.asStateFlow()

    private val deliveryAddressHint =
        resourcesProvider.getString(R.string.hint_create_order_delivery_address)
    private val cafeAddressHint =
        resourcesProvider.getString(R.string.hint_create_order_cafe_address)
    private val mutableAddressHint: MutableStateFlow<String> = MutableStateFlow(deliveryAddressHint)
    val addressHint: StateFlow<String> = mutableAddressHint.asStateFlow()

    private var selectedTimeHour: Int = -1
    private var selectedTimeMinute: Int = -1
    private var deferredTimeValue: Long? = null
    private val asap = resourcesProvider.getString(R.string.msg_deferred_time_asap)
    private val mutableDeferredTime: MutableStateFlow<String> = MutableStateFlow(asap)
    val deferredTime: StateFlow<String> = mutableDeferredTime.asStateFlow()

    private var cartProductList: List<CartProduct> = listOf()
    private val mutableTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val totalCost: StateFlow<String> = mutableTotalCost.asStateFlow()

    private val mutableDeliveryCost: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryCost: StateFlow<String> = mutableDeliveryCost.asStateFlow()

    private val mutableAmountToPay: MutableStateFlow<String> = MutableStateFlow("")
    val amountToPay: StateFlow<String> = mutableAmountToPay.asStateFlow()

    private val mutableIsLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = mutableIsLoading.asStateFlow()

    init {
        subscribeOnAddress()
        subscribeOnCartProduct()
    }

    fun onIsDeliveryChanged(isDelivery: Boolean) {
        mutableIsDelivery.value = isDelivery
        if (isDelivery) {
            mutableDeferredTimeHint.value = deliveryTimeHint
            mutableAddressHint.value = deliveryAddressHint
        } else {
            mutableDeferredTimeHint.value = pickupTimeHint
            mutableAddressHint.value = cafeAddressHint
        }
    }

    fun onDeferredTimeSelected(deferredTimeMillis: Long) {
        deferredTimeValue = deferredTimeMillis
        if (deferredTimeMillis == -1L) {
            selectedTimeHour = -1
            selectedTimeMinute = -1
            mutableDeferredTime.value = asap
        } else {
            selectedTimeHour = dateTimeUtils.getHour(deferredTimeMillis)
            selectedTimeMinute = dateTimeUtils.getMinute(deferredTimeMillis)
            mutableDeferredTime.value = dateTimeUtils.getTimeHHMM(deferredTimeMillis)
        }
    }

    fun onCommentChanged(newComment: String) {
        if (newComment.isEmpty()) {
            mutableComment.value = null
        } else {
            mutableComment.value = newComment
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        viewModelScope.launch {
            userAddressRepo.saveSelectedUserAddress(userAddressUuid)
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        viewModelScope.launch {
            cafeInteractor.selectCafe(cafeUuid)
        }
    }

    fun onCreateOrderClicked() {
        mutableIsLoading.value = true

        val isDelivery = mutableIsDelivery.value
        val selectedAddress = address.value
        if ((isDelivery && (selectedUserAddressUuid == null))
            || (!isDelivery && (selectedCafeUuid == null))
            || selectedAddress == null
        ) {
            showError(resourcesProvider.getString(R.string.error_create_order_address))
            mutableIsLoading.value = false
            return
        }

        viewModelScope.launch {
            if (userInteractor.isUserAuthorize()) {
                showError(resourcesProvider.getString(R.string.error_create_order_user))
                goBack()
                return@launch
            }

            // TODO orderInteractor create order
        }

        viewModelScope.launch {
            val orderDetails = OrderDetails(
                isDelivery = isDelivery,
                profileUuid = "",
                userAddressUuid = selectedUserAddressUuid,
                cafeUuid = selectedCafeUuid,
                address = selectedAddress,
                comment = comment.value,
                deferredTime = deferredTimeValue,
            )
            val order = orderRepo.createOrder(orderDetails)
            if (order == null) {
                showError(
                    resourcesProvider.getString(R.string.error_create_order_something_went_wrong)
                )
                mutableIsLoading.value = false
            } else {
                showMessage(
                    resourcesProvider.getString(R.string.msg_create_order_order_code) + order.code
                )
                router.navigate(toNavProfile())
            }
        }
    }

    private fun subscribeOnAddress() {
        mutableIsDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                userAddressRepo.observeSelectedUserAddress().onEach { userAddress ->
                    logD(ORDER_TAG, "selectedUserAddress = $userAddress")
                    if (userAddress == null) {
                        val userAddressList = userAddressRepo.getUserAddressList()
                        selectedUserAddressUuid = userAddressList.firstOrNull()?.uuid
                        mutableAddress.value =
                            stringUtil.getUserAddressString(userAddressList.firstOrNull())
                    } else {
                        selectedUserAddressUuid = userAddress.uuid
                        mutableAddress.value = stringUtil.getUserAddressString(userAddress)
                    }
                }
            } else {
                cafeRepo.observeSelectedCafe().onEach { cafe ->
                    logD(ORDER_TAG, "selectedCafe = $cafe")
                    if (cafe == null) {
                        val cafeList = cafeRepo.getCafeList()
                        selectedCafeUuid = cafeList.firstOrNull()?.uuid
                        mutableAddress.value =
                            stringUtil.getCafeAddressString(cafeList.firstOrNull())
                    } else {
                        selectedCafeUuid = cafe.uuid
                        mutableAddress.value = stringUtil.getCafeAddressString(cafe)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnCartProduct() {
        cartProductRepo.observeCartProductList().flatMapLatest { productList ->
            cartProductList = productList
            val totalCost = productHelper.getNewTotalCost(productList)
            mutableTotalCost.value = stringUtil.getCostString(totalCost)

            mutableIsDelivery.onEach { isDelivery ->
                val delivery = dataStoreRepo.delivery.first()
                val deliveryCost = orderUtil.getDeliveryCost(isDelivery, productList, delivery)
                mutableDeliveryCost.value = stringUtil.getCostString(deliveryCost)

                val amountToPay = orderUtil.getNewOrderCost(isDelivery, productList, delivery)
                mutableAmountToPay.value = stringUtil.getCostString(amountToPay)
            }
        }.launchIn(viewModelScope)
    }

    fun onAddressClicked() {
        if (isDelivery.value) {
            router.navigate(toNavAddress(true))
        } else {
            router.navigate(toCafeAddressesBottomSheet())
        }
    }

    fun onAddAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    fun onDeferredTimeClicked() {
        router.navigate(
            toDeferredTimeBottomSheet(
                deferredTimeHint.value,
                selectedTimeHour,
                selectedTimeMinute
            )
        )
    }

    fun onAddCommentClicked() {
        val oneLineActionModel = OneLineActionModel(
            title = resourcesProvider.getString(R.string.title_create_order_addition_comment),
            infoText = null,
            hint = resourcesProvider.getString(R.string.hint_create_order_comment),
            type = OneLineActionType.TEXT,
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
            type = OneLineActionType.TEXT,
            inputText = comment.value ?: "",
            buttonText = resourcesProvider.getString(R.string.action_create_order_save_comment),
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }
}