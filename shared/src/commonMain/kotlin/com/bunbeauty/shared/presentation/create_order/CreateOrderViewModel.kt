package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.use_case.address.GetSelectedCafeUseCase
import com.bunbeauty.shared.domain.use_case.address.GetSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.presentation.SharedViewModel
import com.bunbeauty.shared.presentation.cafe_address_list.CafeAddressMapper
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val timeMapper: TimeMapper,
    private val userAddressMapper: UserAddressMapper,
    private val getSelectedUserAddress: GetSelectedUserAddressUseCase,
    private val getSelectedCafe: GetSelectedCafeUseCase,
    private val getUserAddressList: GetUserAddressListUseCase,
    private val getCafeList: GetCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val getMinTime: GetMinTimeUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedViewModel() {

    private val orderCreationData = MutableStateFlow(OrderCreationData())
    private val mutableCreateOrderState = MutableStateFlow(CreateOrderState())
    val orderCreationState = mutableCreateOrderState.asCommonStateFlow()

    fun update() {
        withLoading {
            updateAddresses()
            updateCartTotal()
        }
    }

    fun onSwitcherPositionChanged(position: Int) {
        (position == 0).let { isDelivery ->
            mutableCreateOrderState.update { state ->
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
            CreateOrderState.Event.OpenCreateAddressEvent
        } else {
            CreateOrderState.Event.ShowUserAddressListEvent(
                addressList = addressUiList,
                selectedUserAddressUuid = orderCreationState.value.deliveryAddress?.uuid
            )
        }
        mutableCreateOrderState.update { state ->
            state + event
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        withLoading {
            saveSelectedUserAddressUseCase(userAddressUuid)
            updateSelectedUserAddress()
        }
    }

    fun onCafeAddressClicked() {
        val event = CreateOrderState.Event.ShowCafeAddressListEvent(
            orderCreationData.value.cafeList.map(CafeAddressMapper::toCafeAddressItem),
            selectedCafeAddress = orderCreationState.value.pickupAddress
        )
        mutableCreateOrderState.update { state ->
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
        sharedScope.launch {
            val timeZone = getSelectedCityTimeZoneUseCase()
            mutableCreateOrderState.update { state ->
                val event = CreateOrderState.Event.ShowDeferredTimeEvent(
                    deferredTime = deferredTime,
                    minTime = timeMapper.toUiModel(getMinTime(timeZone)),
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
        mutableCreateOrderState.update { state ->
            state.copy(deferredTime = deferredTimeUi)
        }
    }

    fun onCommentClicked() {
        val event = CreateOrderState.Event.ShowCommentInputEvent(
            comment = mutableCreateOrderState.value.comment
        )
        mutableCreateOrderState.update { state ->
            state + event
        }
    }

    fun onCommentChanged(comment: String) {
        mutableCreateOrderState.update { state ->
            state.copy(comment = comment.ifEmpty { null })
        }
    }

    fun onCreateOrderClicked() {
        val stateValue = mutableCreateOrderState.value
        val data = orderCreationData.value

        val isDeliveryAddressNotSelected =
            stateValue.isDelivery && (data.selectedUserAddress == null)
        mutableCreateOrderState.update { state ->
            state.copy(isAddressErrorShown = isDeliveryAddressNotSelected)
        }

        if (isDeliveryAddressNotSelected) {
            mutableCreateOrderState.update { state ->
                state + CreateOrderState.Event.ShowUserAddressError
            }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = createOrderUseCase(
                    isDelivery = stateValue.isDelivery,
                    selectedUserAddress = data.selectedUserAddress,
                    selectedCafe = data.selectedCafe,
                    comment = stateValue.comment,
                    deferredTime = data.selectedDeferredTime,
                    timeZone = getSelectedCityTimeZoneUseCase()
                )
                if (orderCode == null) {
                    val event = CreateOrderState.Event.ShowSomethingWentWrongErrorEvent
                    mutableCreateOrderState.update { it + event }
                } else {
                    cartProductInteractor.removeAllProductsFromCart()
                    // Delay for IOS version,
                    // Try to remove it when ConsumerCartViewModel.kt will be shared
                    //delay(50)
                    mutableCreateOrderState.update {
                        it + CreateOrderState.Event.OrderCreatedEvent(
                            code = orderCode.code
                        )
                    }
                }
            } else {
                val event = CreateOrderState.Event.ShowUserUnauthorizedErrorEvent
                mutableCreateOrderState.update { it + event }
            }
        }
    }

    fun consumeEventList(eventList: List<CreateOrderState.Event>) {
        mutableCreateOrderState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private suspend fun updateAddresses() {
        val userAddressList = getUserAddressList()
        val cafeList = getCafeList()
        orderCreationData.update { data ->
            data.copy(
                userAddressList = userAddressList,
                cafeList = cafeList
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
        mutableCreateOrderState.update { state ->
            state.copy(deliveryAddress = userAddressMapper.toUiModel(selectedUserAddress))
        }
        if (selectedUserAddress != null) {
            mutableCreateOrderState.update { state ->
                state.copy(isAddressErrorShown = false)
            }
        }
    }

    private suspend fun updateSelectedCafe() {
        val selectedCafe = getSelectedCafe()
        orderCreationData.update { data ->
            data.copy(selectedCafe = selectedCafe)
        }
        mutableCreateOrderState.update { state ->
            state.copy(pickupAddress = selectedCafe?.address)
        }
    }

    private suspend fun updateCartTotal() {
        try {
            val isDelivery = mutableCreateOrderState.value.isDelivery
            val cartTotal = getCartTotal(isDelivery)
            mutableCreateOrderState.update { state ->
                state.copy(
                    totalCost = cartTotal.totalCost,
                    deliveryCost = cartTotal.deliveryCost,
                    finalCost = cartTotal.finalCost,
                )
            }
        } catch (exception: Exception) {
            val event = CreateOrderState.Event.ShowSomethingWentWrongErrorEvent
            mutableCreateOrderState.update { state ->
                state + event
            }
        }
    }

    private inline fun withLoading(crossinline block: suspend () -> Unit) {
        //TODO (Exception handler)
        sharedScope.launch {
            mutableCreateOrderState.update { state ->
                state.copy(isLoading = true)
            }
            block()
            mutableCreateOrderState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }
}
