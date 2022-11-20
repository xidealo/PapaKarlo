package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.create_order.mapper.TimeMapper
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressMapper
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextSettings
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.Constants.COMMENT_REQUEST_KEY
import com.bunbeauty.shared.Constants.RESULT_COMMENT_KEY
import com.bunbeauty.shared.domain.interactor.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateOrderViewModel(
    private val addressInteractor: IAddressInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val orderInteractor: IOrderInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    private val stringUtil: IStringUtil,
    private val timeMapper: TimeMapper,
    private val userAddressMapper: UserAddressMapper,
    private val getSelectedUserAddress: GetSelectedUserAddressUseCase,
    private val getSelectedCafe: GetSelectedCafeUseCase,
    private val getUserAddressList: GetUserAddressListUseCase,
    private val getCafeList: GetCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val resources: Resources
) : BaseViewModel() {

    private val orderCreationData = MutableStateFlow(OrderCreationData())
    private val mutableOrderCreationState = MutableStateFlow(OrderCreationUiState())
    val orderCreationState = mutableOrderCreationState.asStateFlow()

    private val asap by lazy {
        resources.getString(R.string.asap)
    }

    fun update() {
        withLoading {
            updateAddresses()
            updateCartTotal()
        }
    }

    fun onSwitcherPositionChanged(position: Int) {
        (position == 0).let { isDelivery ->
            mutableOrderCreationState.update { state ->
                state.copy(
                    isDelivery = isDelivery,
                    addressLabelId = if (isDelivery) {
                        R.string.delivery_address
                    } else {
                        R.string.cafe_address
                    },
                    deferredTimeLabelId = if (isDelivery) {
                        R.string.delivery_time
                    } else {
                        R.string.pickup_time
                    }
                )
            }
        }
    }

    fun onUserAddressClicked() {
        val addressList = orderCreationData.value.userAddressList
        val event = if (addressList.isEmpty()) {
            OrderCreationUiState.Event.OpenCreateAddressEvent
        } else {
            OrderCreationUiState.Event.OpenUserAddressListEvent(
                addressList.map(userAddressMapper::toUserAddressItem)
            )
        }
        mutableOrderCreationState.update { state ->
            state + event
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        withLoading {
            addressInteractor.saveSelectedUserAddress(userAddressUuid)
            updateSelectedUserAddress()
        }
    }

    fun onCafeAddressClicked() {
        mutableOrderCreationState.update { state ->
            state + OrderCreationUiState.Event.ShowCafeAddressListEvent
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateSelectedUserAddress()
        }
    }

    fun onDeferredTimeClicked() {
        val title = resources.getString(mutableOrderCreationState.value.deferredTimeLabelId)
        val deferredTime = orderCreationData.value.selectedDeferredTime?.let { time ->
            timeMapper.toUiModel(time)
        }
        val event = OrderCreationUiState.Event.ShowDeferredTimeEvent(title, deferredTime)
        mutableOrderCreationState.update { state ->
            state + event
        }
    }

    fun onDeferredTimeSelected(deferredTimeUi: TimeUI?) {
        val deferredTime = deferredTimeUi?.let { timeMapper.toDomainModel(deferredTimeUi) }
        orderCreationData.update { data ->
            data.copy(selectedDeferredTime = deferredTime)
        }
        val deferredTimeString = deferredTime?.let {
            stringUtil.getTimeString(deferredTime)
        } ?: asap
        mutableOrderCreationState.update { state ->
            state.copy(deferredTime = deferredTimeString)
        }
    }

    fun onCommentClicked() {
        val titleStringId = if (mutableOrderCreationState.value.comment == null) {
            R.string.title_create_order_addition_comment
        } else {
            R.string.title_create_order_editing_comment
        }
        val inputSettings = EditTextSettings(
            titleStringId = titleStringId,
            infoText = null,
            labelStringId = R.string.hint_create_order_comment,
            type = EditTextType.TEXT,
            inputText = mutableOrderCreationState.value.comment,
            buttonStringId = R.string.action_create_order_save_comment,
            requestKey = COMMENT_REQUEST_KEY,
            resultKey = RESULT_COMMENT_KEY
        )
        val event = OrderCreationUiState.Event.ShowCommentInputEvent(inputSettings = inputSettings)
        mutableOrderCreationState.update { state ->
            state + event
        }
    }

    fun onCommentChanged(comment: String) {
        mutableOrderCreationState.update { state ->
            state.copy(comment = comment.ifEmpty { null })
        }
    }

    fun onCreateOrderClicked() {
        val state = mutableOrderCreationState.value
        val data = orderCreationData.value
        val address = if (state.isDelivery) {
            stringUtil.getUserAddressString(data.selectedUserAddress)
        } else {
            data.selectedCafe?.address
        }
        if (address == null) {
            val event = OrderCreationUiState.Event.ShowAddressErrorEvent(
                message = resources.getString(R.string.error_create_order_address)
            )
            mutableOrderCreationState.update { it + event }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = orderInteractor.createOrder(
                    isDelivery = state.isDelivery,
                    userAddressUuid = data.selectedUserAddress?.uuid,
                    cafeUuid = data.selectedCafe?.uuid,
                    addressDescription = address,
                    comment = state.comment,
                    deferredTime = data.selectedDeferredTime?.let { deferredTime ->
                        deferredTimeInteractor.getDeferredTimeMillis(deferredTime)
                    },
                )
                if (orderCode == null) {
                    val event = OrderCreationUiState.Event.ShowErrorEvent(
                        message = resources.getString(R.string.error_something_went_wrong)
                    )
                    mutableOrderCreationState.update { it + event }
                } else {
                    cartProductInteractor.removeAllProductsFromCart()

                    val event = OrderCreationUiState.Event.OrderCreatedEvent(code = orderCode.code)
                    mutableOrderCreationState.update { it + event }
                }
            } else {
                val event = OrderCreationUiState.Event.ShowErrorEvent(
                    message = resources.getString(R.string.error_create_order_user)
                )
                mutableOrderCreationState.update { it + event }
            }
        }
    }

    fun consumeEventList(eventList: List<OrderCreationUiState.Event>) {
        mutableOrderCreationState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private suspend fun updateAddresses() {
        val userAddressList = getUserAddressList()
        val cafeList = getCafeList()
        orderCreationData.update { data ->
            data.copy(
                userAddressList = userAddressList, cafeList = cafeList
            )
        }

        updateSelectedUserAddress()
        updateSelectedCafe()
    }

    private suspend fun updateSelectedUserAddress() {
        val selectedUserAddress = getSelectedUserAddress()
        orderCreationData.update { data ->
            data.copy(selectedUserAddress = selectedUserAddress)
        }
        mutableOrderCreationState.update { state ->
            state.copy(deliveryAddress = stringUtil.getUserAddressString(selectedUserAddress))
        }
    }

    private suspend fun updateSelectedCafe() {
        val selectedCafe = getSelectedCafe()
        orderCreationData.update { data ->
            data.copy(selectedCafe = selectedCafe)
        }
        mutableOrderCreationState.update { state ->
            state.copy(pickupAddress = selectedCafe?.address)
        }
    }

    private suspend fun updateCartTotal() {
        try {
            val cartTotal = getCartTotal()
            mutableOrderCreationState.update { state ->
                state.copy(
                    totalCost = stringUtil.getCostString(cartTotal.totalCost),
                    deliveryCost = stringUtil.getCostString(cartTotal.deliveryCost),
                    finalCost = stringUtil.getCostString(cartTotal.finalCost)
                )
            }
        } catch (exception: Exception) {
            val event = OrderCreationUiState.Event.ShowErrorEvent(
                message = exception.message
                    ?: resources.getString(R.string.error_something_went_wrong)
            )
            mutableOrderCreationState.update { state ->
                state + event
            }
        }
    }

    private inline fun withLoading(crossinline block: suspend () -> Unit) {
        viewModelScope.launch {
            mutableOrderCreationState.update { state ->
                state.copy(isLoading = true)
            }
            block()
            mutableOrderCreationState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }
}