package com.bunbeauty.shared.presentation.create_order

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.data.mapper.user_address.UserAddressMapper
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.extension.mapToStateFlow
import com.bunbeauty.shared.presentation.base.SharedViewModel
import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItemMapper
import com.bunbeauty.shared.presentation.create_order.model.TimeUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val createOrderStateMapper: CreateOrderStateMapper,
    private val timeMapper: TimeMapper,
    private val userAddressMapper: UserAddressMapper,
    private val getSelectableUserAddressList: GetSelectableUserAddressListUseCase,
    private val getSelectableCafeList: GetSelectableCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val getMinTime: GetMinTimeUseCase,
    private val createOrder: CreateOrderUseCase,
    private val getSelectedCityTimeZone: GetSelectedCityTimeZoneUseCase,
    private val saveSelectedUserAddress: SaveSelectedUserAddressUseCase,
    private val getSelectablePaymentMethodListUseCase: GetSelectablePaymentMethodListUseCase,
    private val savePaymentMethodUseCase: SavePaymentMethodUseCase,
) : SharedViewModel() {

    private val mutableDataState = MutableStateFlow(
        CreateOrderDataState(
            discount = null
        )
    )

    val uiState = mutableDataState.mapToStateFlow(
        scope = sharedScope,
        block = createOrderStateMapper::map
    )

    fun update() {
        withLoading {
            updateAddresses()
            updatePaymentMethods()
            updateCartTotal()
        }
    }

    fun onSwitcherPositionChanged(position: Int) {
        (position == 0).let { isDelivery ->
            mutableDataState.update { state ->
                state.copy(isDelivery = isDelivery)
            }
        }
        withLoading {
            updateCartTotal()
        }
    }

    fun onUserAddressClicked() {
        val addressList = mutableDataState.value.userAddressList
        val addressUiList = addressList.mapNotNull(userAddressMapper::toUiModel)
        val event = if (addressUiList.isEmpty()) {
            CreateOrderEvent.OpenCreateAddressEvent
        } else {
            CreateOrderEvent.ShowUserAddressListEvent(addressList = addressUiList)
        }
        mutableDataState.update { state ->
            state + event
        }
    }

    fun onUserAddressChanged(userAddressUuid: String) {
        withLoading {
            saveSelectedUserAddress(userAddressUuid)
            updateSelectedUserAddress()
        }
    }

    fun onCafeAddressClicked() {
        val event = CreateOrderEvent.ShowCafeAddressListEvent(
            mutableDataState.value.cafeList.map(SelectableCafeAddressItemMapper::toSelectableCafeAddressItem),
        )
        mutableDataState.update { state ->
            state + event
        }
    }

    fun onCafeAddressChanged(cafeUuid: String) {
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateSelectedCafe()
        }
    }

    fun onPaymentMethodChanged(paymentMethodUuid: String) {
        withLoading {
            savePaymentMethodUseCase(paymentMethodUuid)
            updatePaymentMethods()
        }
    }

    fun onDeferredTimeClicked() {
        sharedScope.launchSafe(
            block = {
                val timeZone = getSelectedCityTimeZone()
                mutableDataState.update { state ->
                    val event = CreateOrderEvent.ShowDeferredTimeEvent(
                        deferredTime = timeMapper.toUiModel(state.deferredTime),
                        minTime = timeMapper.toUiModel(getMinTime(timeZone)),
                        isDelivery = state.isDelivery
                    )
                    state + event
                }
            },
            onError = {}
        )
    }

    fun onPaymentMethodClick() {
        mutableDataState.update { state ->
            val event = CreateOrderEvent.ShowPaymentMethodList(
                selectablePaymentMethodList = state.paymentMethodList
            )
            state + event
        }
    }

    fun onDeferredTimeSelected(deferredTimeUi: TimeUI) {
        mutableDataState.update { data ->
            data.copy(deferredTime = timeMapper.toDomainModel(deferredTimeUi))
        }
    }

    fun onCommentClicked() {
        val event = CreateOrderEvent.ShowCommentInputEvent(
            comment = mutableDataState.value.comment
        )
        mutableDataState.update { state ->
            state + event
        }
    }

    fun onCommentChanged(comment: String) {
        mutableDataState.update { state ->
            state.copy(comment = comment.ifEmpty { null })
        }
    }

    fun onCreateOrderClicked() {
        val stateValue = mutableDataState.value
        val data = mutableDataState.value

        val isDeliveryAddressNotSelected =
            stateValue.isDelivery && (data.selectedUserAddress == null)
        mutableDataState.update { state ->
            state.copy(isUserAddressErrorShown = isDeliveryAddressNotSelected)
        }

        if (isDeliveryAddressNotSelected) {
            mutableDataState.update { state ->
                state + CreateOrderEvent.ShowUserAddressError
            }
            return
        }

        if (data.selectedPaymentMethod == null) {
            mutableDataState.update { state ->
                state + CreateOrderEvent.ShowPaymentMethodError
            }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = createOrder(
                    isDelivery = stateValue.isDelivery,
                    selectedUserAddress = data.selectedUserAddress,
                    selectedCafe = data.selectedCafe,
                    comment = stateValue.comment,
                    deferredTime = data.deferredTime,
                    timeZone = getSelectedCityTimeZone(),
                    paymentMethod = data.selectedPaymentMethod.name.name
                )
                if (orderCode == null) {
                    val event = CreateOrderEvent.ShowSomethingWentWrongErrorEvent
                    mutableDataState.update { state ->
                        state + event
                    }
                } else {
                    cartProductInteractor.removeAllProductsFromCart()
                    mutableDataState.update { state ->
                        state + CreateOrderEvent.OrderCreatedEvent(
                            code = orderCode.code
                        )
                    }
                }
            } else {
                val event = CreateOrderEvent.ShowUserUnauthorizedErrorEvent
                mutableDataState.update { state ->
                    state + event
                }
            }
        }
    }

    fun consumeEventList(eventList: List<CreateOrderEvent>) {
        mutableDataState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }

    private suspend fun updateAddresses() {
        val userAddressList = getSelectableUserAddressList()
        val cafeList = getSelectableCafeList()
        mutableDataState.update { data ->
            data.copy(
                userAddressList = userAddressList,
                cafeList = cafeList,
            )
        }
        updateSelectedUserAddress()
        updateSelectedCafe()
    }

    private suspend fun updatePaymentMethods() {
        val paymentMethodList = getSelectablePaymentMethodListUseCase()
        mutableDataState.update { data ->
            data.copy(
                paymentMethodList = paymentMethodList,
                selectedPaymentMethod = paymentMethodList.find { it.isSelected }?.paymentMethod
            )
        }
    }

    private suspend fun updateSelectedUserAddress() {
        val userAddressList = getSelectableUserAddressList()
        mutableDataState.update { data ->
            data.copy(userAddressList = userAddressList)
        }
        val selectableUserAddress = userAddressList.find { it.isSelected }

        mutableDataState.update { data ->
            data.copy(selectedUserAddress = selectableUserAddress)
        }
        if (selectableUserAddress != null) {
            mutableDataState.update { state ->
                state.copy(isUserAddressErrorShown = false)
            }
        }
    }

    private suspend fun updateSelectedCafe() {
        val cafeList = getSelectableCafeList()
        mutableDataState.update { data ->
            data.copy(
                cafeList = cafeList,
            )
        }
        val selectedCafe = cafeList.find { it.isSelected }

        mutableDataState.update { data ->
            data.copy(selectedCafe = selectedCafe)
        }
    }

    private suspend fun updateCartTotal() {
        try {
            val isDelivery = mutableDataState.value.isDelivery
            val cartTotal = getCartTotal(isDelivery)
            mutableDataState.update { state ->
                state.copy(
                    totalCost = cartTotal.totalCost,
                    deliveryCost = cartTotal.deliveryCost,
                    newFinalCost = cartTotal.newFinalCost,
                    oldFinalCost = cartTotal.oldFinalCost,
                    discount = cartTotal.discount?.let { discount ->
                        discount.toString() + PERCENT
                    }
                )
            }
        } catch (exception: Exception) {
            val event = CreateOrderEvent.ShowSomethingWentWrongErrorEvent
            mutableDataState.update { state ->
                state + event
            }
        }
    }

    private inline fun withLoading(crossinline block: suspend () -> Unit) {
        sharedScope.launchSafe(
            {
                mutableDataState.update { state ->
                    state.copy(isLoading = true)
                }
                block()
                mutableDataState.update { state ->
                    state.copy(isLoading = false)
                }
            },
            onError = {

            }
        )
    }
}
