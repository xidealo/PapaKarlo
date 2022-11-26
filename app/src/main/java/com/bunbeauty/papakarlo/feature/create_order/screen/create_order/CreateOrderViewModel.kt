package com.bunbeauty.papakarlo.feature.create_order.screen.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.feature.create_order.mapper.CafeAddressMapper
import com.bunbeauty.papakarlo.feature.create_order.mapper.TimeMapper
import com.bunbeauty.papakarlo.feature.create_order.mapper.UserAddressMapper
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.domain.interactor.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.interactor.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.shared.domain.interactor.order.IOrderInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.ui.SharedViewModel
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
    private val timeMapper: TimeMapper,
    private val userAddressMapper: UserAddressMapper,
    private val getSelectedUserAddress: GetSelectedUserAddressUseCase,
    private val getSelectedCafe: GetSelectedCafeUseCase,
    private val getUserAddressList: GetUserAddressListUseCase,
    private val getCafeList: GetCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val getMinTime: GetMinTimeUseCase
) : SharedViewModel() {

    private val orderCreationData = MutableStateFlow(OrderCreationData())
    private val mutableOrderCreationState = MutableStateFlow(OrderCreationUiState())
    val orderCreationState = mutableOrderCreationState.asStateFlow()

    fun update() {
        withLoading {
            updateAddresses()
            updateCartTotal()
        }
    }

    fun onSwitcherPositionChanged(position: Int) {
        (position == 0).let { isDelivery ->
            mutableOrderCreationState.update { state ->
                state.copy(isDelivery = isDelivery)
            }
        }
        withLoading {
            updateCartTotal()
        }
    }

    fun onUserAddressClicked() {
        val addressList = orderCreationData.value.userAddressList
        val addressUiList = addressList.mapNotNull(userAddressMapper::toUiModel)
        val event = if (addressUiList.isEmpty()) {
            OrderCreationUiState.Event.OpenCreateAddressEvent
        } else {
            OrderCreationUiState.Event.ShowUserAddressListEvent(addressUiList)
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
        val event = OrderCreationUiState.Event.ShowCafeAddressListEvent(
            orderCreationData.value.cafeList.map(CafeAddressMapper::toCafeAddressItem)
        )
        mutableOrderCreationState.update { state ->
            state + event
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateSelectedCafe()
        }
    }

    fun onDeferredTimeClicked() {
        val deferredTime = timeMapper.toUiModel(orderCreationData.value.selectedDeferredTime)
        viewModelScope.launch {
            mutableOrderCreationState.update { state ->
                val event = OrderCreationUiState.Event.ShowDeferredTimeEvent(
                    deferredTime = deferredTime,
                    minTime = timeMapper.toUiModel(getMinTime()),
                    isDelivery = state.isDelivery
                )
                state + event
            }
        }
    }

    fun onDeferredTimeSelected(deferredTimeUi: TimeUI) {
        val deferredTime = timeMapper.toDomainModel(deferredTimeUi)
        orderCreationData.update { data ->
            data.copy(selectedDeferredTime = deferredTime)
        }
        mutableOrderCreationState.update { state ->
            state.copy(deferredTime = deferredTimeUi)
        }
    }

    fun onCommentClicked() {
        val event = OrderCreationUiState.Event.ShowCommentInputEvent(
            comment = mutableOrderCreationState.value.comment
        )
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
        val stateValue = mutableOrderCreationState.value
        val data = orderCreationData.value
        val address = if (stateValue.isDelivery) {
            "" // TODO send address data instead of string
            // stringUtil.getUserAddressString(data.selectedUserAddress)
        } else {
            data.selectedCafe?.address
        }
        mutableOrderCreationState.update { state ->
            val isDeliveryAddressNotSelected =
                stateValue.isDelivery && (data.selectedUserAddress == null)
            state.copy(isAddressErrorShown = isDeliveryAddressNotSelected)
        }
        if (address == null) {
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = orderInteractor.createOrder(
                    isDelivery = stateValue.isDelivery,
                    userAddressUuid = data.selectedUserAddress?.uuid,
                    cafeUuid = data.selectedCafe?.uuid,
                    addressDescription = address,
                    comment = stateValue.comment,
                    deferredTime = data.selectedDeferredTime?.let { deferredTime ->
                        deferredTimeInteractor.getDeferredTimeMillis(deferredTime)
                    },
                )
                if (orderCode == null) {
                    val event = OrderCreationUiState.Event.ShowUserUnauthorizedErrorEvent
                    mutableOrderCreationState.update { it + event }
                } else {
                    cartProductInteractor.removeAllProductsFromCart()

                    val event = OrderCreationUiState.Event.OrderCreatedEvent(code = orderCode.code)
                    mutableOrderCreationState.update { it + event }
                }
            } else {
                val event = OrderCreationUiState.Event.ShowUserUnauthorizedErrorEvent
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
            state.copy(deliveryAddress = userAddressMapper.toUiModel(selectedUserAddress))
        }
        if (selectedUserAddress != null) {
            mutableOrderCreationState.update { state ->
                state.copy(isAddressErrorShown = false)
            }
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
            val isDelivery = mutableOrderCreationState.value.isDelivery
            val cartTotal = getCartTotal(isDelivery)
            mutableOrderCreationState.update { state ->
                state.copy(
                    totalCost = cartTotal.totalCost,
                    deliveryCost = cartTotal.deliveryCost,
                    finalCost = cartTotal.finalCost,
                )
            }
        } catch (exception: Exception) {
            val event = OrderCreationUiState.Event.ShowSomethingWentWrongErrorEvent
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
