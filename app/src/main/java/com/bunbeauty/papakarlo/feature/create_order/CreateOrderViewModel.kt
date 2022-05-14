package com.bunbeauty.papakarlo.feature.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.shared.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.create_order.CreateOrderFragmentDirections.*
import com.bunbeauty.papakarlo.feature.edit_text.EditTextSettings
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.shared.Constants.RESULT_COMMENT_KEY
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreateOrderViewModel(
    private val addressInteractor: IAddressInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val orderInteractor: IOrderInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val asap = resourcesProvider.getString(R.string.msg_deferred_time_asap)
    private val mutableOrderCreationUI: MutableStateFlow<OrderCreationUI> = MutableStateFlow(
        OrderCreationUI(
            isDelivery = true,
            address = null,
            comment = null,
            deferredTime = asap,
            totalCost = null,
            deliveryCost = null,
            amountToPay = null,
            isLoading = false
        )
    )
    val orderCreationUI: StateFlow<OrderCreationUI> = mutableOrderCreationUI.asStateFlow()
    private val isDelivery: Flow<Boolean> = mutableOrderCreationUI.map { orderCreationUI ->
        orderCreationUI.isDelivery
    }
    private var selectedUserAddressUuid: String? = null
    private var selectedCafeUuid: String? = null
    private var selectedDeferredTimeMillis: Long? = null
    private var selectedDeferredTime: Time? = null

    init {
        observeAddress()
        observeCartTotal()
    }

    fun onSwitcherPositionChanged(position: Int) {
        mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(isDelivery = position == 0)
    }

    fun onDeferredTimeSelected(deferredTime: Time?) {
        selectedDeferredTime = deferredTime
        if (deferredTime == null) {
            selectedDeferredTimeMillis = null
            mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(deferredTime = asap)
        } else {
            viewModelScope.launch {
                selectedDeferredTimeMillis =
                    deferredTimeInteractor.getDeferredTimeMillis(deferredTime)
                mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(
                    deferredTime = stringUtil.getTimeString(deferredTime)
                )
            }
        }
    }

    fun onCommentChanged(comment: String) {
        if (comment.isEmpty()) {
            mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(comment = null)
        } else {
            mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(comment = comment)
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        viewModelScope.launch {
            addressInteractor.saveSelectedUserAddress(userAddressUuid)
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        viewModelScope.launch {
            cafeInteractor.selectCafe(cafeUuid)
        }
    }

    fun onCreateOrderClicked(orderCreationUI: OrderCreationUI) {
        startLoading()
        if ((orderCreationUI.isDelivery && (selectedUserAddressUuid == null))
            || (!orderCreationUI.isDelivery && (selectedCafeUuid == null))
            || orderCreationUI.address == null
        ) {
            showError(resourcesProvider.getString(R.string.error_create_order_address), false)
            finishLoading()
            return
        }

        viewModelScope.launch {
            if (!userInteractor.isUserAuthorize()) {
                showError(resourcesProvider.getString(R.string.error_create_order_user), false)
                goBack()
                return@launch
            }

            val orderCode = orderInteractor.createOrder(
                isDelivery = orderCreationUI.isDelivery,
                userAddressUuid = selectedUserAddressUuid,
                cafeUuid = selectedCafeUuid,
                addressDescription = orderCreationUI.address,
                comment = orderCreationUI.comment,
                deferredTime = selectedDeferredTimeMillis,
            )

            if (orderCode == null) {
                showError(
                    resourcesProvider.getString(R.string.error_create_order_something_went_wrong),
                    false
                )
                finishLoading()
            } else {
                cartProductInteractor.removeAllProductsFromCart()
                showMessage(
                    resourcesProvider.getString(R.string.msg_create_order_order_code) + orderCode.code,
                    false
                )
                router.navigate(toProfileFragment())
            }
        }
    }

    fun onChangeAddressClicked() {
        if (mutableOrderCreationUI.value.isDelivery) {
            router.navigate(toNavAddress(true))
        } else {
            router.navigate(toCafeAddressesBottomSheet())
        }
    }

    fun onAddAddressClicked() {
        router.navigate(toCreateAddressFragment())
    }

    fun onDeferredTimeClicked() {
        val deferredTimeHint = if (mutableOrderCreationUI.value.isDelivery) {
            resourcesProvider.getString(R.string.hint_create_order_delivery_time)
        } else {
            resourcesProvider.getString(R.string.hint_create_order_pickup_time)
        }
        //TODO(Change parametrs)
        //router.navigate(toDeferredTimeBottomSheet(deferredTimeHint, selectedDeferredTime))
    }

    fun onAddCommentClicked() {
        val oneLineActionModel = EditTextSettings(
            titleStringId = R.string.title_create_order_addition_comment,
            infoText = null,
            labelStringId = R.string.hint_create_order_comment,
            type = EditTextType.TEXT,
            inputText = "",
            buttonStringId = R.string.action_create_order_save_comment,
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    fun onEditCommentClicked() {
        val oneLineActionModel = EditTextSettings(
            titleStringId = R.string.title_create_order_editing_comment,
            infoText = null,
            labelStringId = R.string.hint_create_order_comment,
            type = EditTextType.TEXT,
            inputText = mutableOrderCreationUI.value.comment ?: "",
            buttonStringId = R.string.action_create_order_save_comment,
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        router.navigate(toOneLineActionBottomSheet(oneLineActionModel))
    }

    private fun startLoading() {
        mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(isLoading = true)
    }

    private fun finishLoading() {
        mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(isLoading = false)
    }

    private fun observeAddress() {
        isDelivery.flatMapLatest { isDelivery ->
            if (isDelivery) {
                addressInteractor.observeAddress().onEach { userAddress ->
                    mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(
                        address = stringUtil.getUserAddressString(userAddress)
                    )
                    selectedUserAddressUuid = userAddress?.uuid
                }
            } else {
                cafeInteractor.observeSelectedCafeAddress().onEach { cafeAddress ->
                    mutableOrderCreationUI.value =
                        mutableOrderCreationUI.value.copy(address = cafeAddress.address)
                    selectedCafeUuid = cafeAddress.cafeUuid
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observeCartTotal() {
        val isDelivery = mutableOrderCreationUI.map { orderCreationUI ->
            orderCreationUI.isDelivery
        }
        cartProductInteractor.observeCartTotal(isDelivery).launchOnEach { cartTotal ->
            mutableOrderCreationUI.value = mutableOrderCreationUI.value.copy(
                totalCost = stringUtil.getCostString(cartTotal.totalCost),
                deliveryCost = stringUtil.getCostString(cartTotal.deliveryCost),
                amountToPay = stringUtil.getCostString(cartTotal.amountToPay)
            )
        }
    }
}