package com.bunbeauty.shared.presentation.createorder

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.cafe_address_list.SelectableCafeAddressItemMapper
import kotlinx.coroutines.flow.update

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val getSelectableUserAddressList: GetSelectableUserAddressListUseCase,
    private val getSelectableCafeList: GetSelectableCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val getMinTime: GetMinTimeUseCase,
    private val createOrder: CreateOrderUseCase,
    private val getSelectedCityTimeZone: GetSelectedCityTimeZoneUseCase,
    private val saveSelectedUserAddress: SaveSelectedUserAddressUseCase,
    private val getSelectablePaymentMethodListUseCase: GetSelectablePaymentMethodListUseCase,
    private val savePaymentMethodUseCase: SavePaymentMethodUseCase,
) : SharedStateViewModel<CreateOrder.DataState, CreateOrder.Action, CreateOrder.Event>(
    initDataState = CreateOrder.DataState(
        isDelivery = true,
        userAddressList = emptyList(),
        selectedUserAddress = null,
        isUserAddressError = false,
        cafeList = emptyList(),
        selectedCafe = null,
        comment = "",
        deferredTime = CreateOrder.DeferredTime.Asap,
        paymentMethodList = emptyList(),
        selectedPaymentMethod = null,
        cartTotal = CreateOrder.CartTotal.Loading,
        isLoading = true,
    )
) {

    override fun reduce(action: CreateOrder.Action, dataState: CreateOrder.DataState) {
        when (action) {
            CreateOrder.Action.Update -> {
                update()
            }

            is CreateOrder.Action.ChangeMethod -> {
                changeMethod(position = action.position)
            }

            CreateOrder.Action.UserAddressClick -> {
                userAddressClick()
            }

            is CreateOrder.Action.ChangeUserAddress -> {
                changeUserAddress(userAddressUuid = action.userAddressUuid)
            }

            CreateOrder.Action.CafeAddressClick -> {
                cafeAddressClick()
            }

            is CreateOrder.Action.ChangeCafeAddress -> {
                changeCafeAddress(cafeUuid = action.cafeUuid)
            }

            CreateOrder.Action.PaymentMethodClick -> {
                paymentMethodClick()
            }

            is CreateOrder.Action.ChangePaymentMethod -> {
                changePaymentMethod(paymentMethodUuid = action.paymentMethodUuid)
            }

            CreateOrder.Action.DeferredTimeClick -> {
                deferredTimeClick()
            }

            is CreateOrder.Action.ChangeDeferredTime -> {
                changeDeferredTime(deferredTime = action.deferredTime)
            }

            CreateOrder.Action.CommentClick -> {
                commentClick()
            }

            is CreateOrder.Action.ChangeComment -> {
                onCommentChanged(comment = action.comment)
            }

            CreateOrder.Action.CreateClick -> {
                createClick()
            }
        }
    }

    private fun update() {
        withLoading {
            updateUserAddresses()
        }
        withLoading {
            updateCafeAddresses()
        }
        withLoading {
            updatePaymentMethods()
        }
        withLoading {
            updateCartTotal()
        }
    }

    private fun changeMethod(position: Int) {
        (position == 0).let { isDelivery ->
            setState {
                copy(isDelivery = isDelivery)
            }
        }
        withLoading {
            updateCartTotal()
        }
    }

    private fun userAddressClick() {
        addEvent { state ->
            if (state.userAddressList.isEmpty()) {
                CreateOrder.Event.OpenCreateAddressEvent
            } else {
                CreateOrder.Event.ShowUserAddressListEvent(state.userAddressList)
            }
        }
    }

    private fun changeUserAddress(userAddressUuid: String) {
        withLoading {
            saveSelectedUserAddress(userAddressUuid)
            updateSelectedUserAddress()
        }
    }

    private fun cafeAddressClick() {
        addEvent { state ->
            CreateOrder.Event.ShowCafeAddressListEvent(
                state.cafeList.map(SelectableCafeAddressItemMapper::toSelectableCafeAddressItem),
            )
        }
    }

    private fun changeCafeAddress(cafeUuid: String) {
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateSelectedCafe()
        }
    }

    private fun paymentMethodClick() {
        addEvent { state ->
            CreateOrder.Event.ShowPaymentMethodList(
                selectablePaymentMethodList = state.paymentMethodList
            )
        }
    }

    private fun changePaymentMethod(paymentMethodUuid: String) {
        withLoading {
            savePaymentMethodUseCase(paymentMethodUuid)
            updatePaymentMethods()
        }
    }

    private fun deferredTimeClick() {
        sharedScope.launchSafe(
            block = {
                val timeZone = getSelectedCityTimeZone()
                addEvent { state ->
                    CreateOrder.Event.ShowDeferredTimeEvent(
                        deferredTime = state.deferredTime,
                        minTime = getMinTime(timeZone),
                        isDelivery = state.isDelivery
                    )
                }
            },
            onError = {}
        )
    }

    private fun changeDeferredTime(deferredTime: Time?) {
        setState {
            copy(
                deferredTime = if (deferredTime == null) {
                    CreateOrder.DeferredTime.Asap
                } else {
                    CreateOrder.DeferredTime.Later(
                        time = deferredTime
                    )
                }
            )
        }
    }

    private fun commentClick() {
        addEvent { state ->
            CreateOrder.Event.ShowCommentInputEvent(
                comment = state.comment
            )
        }
    }

    private fun onCommentChanged(comment: String) {
        setState {
            copy(comment = comment.ifEmpty { null })
        }
    }

    private fun createClick() {
        val state = mutableDataState.value

        val isDeliveryAddressNotSelected = state.isDelivery && (state.selectedUserAddress == null)
        setState {
            copy(isUserAddressError = isDeliveryAddressNotSelected)
        }

        if (isDeliveryAddressNotSelected) {
            addEvent {
                CreateOrder.Event.ShowUserAddressError
            }
            return
        }

        val isPaymentMethodNotSelected = state.selectedPaymentMethod == null
        setState {
            copy(isPaymentMethodError = isPaymentMethodNotSelected)
        }
        if (isPaymentMethodNotSelected) {
            addEvent {
                CreateOrder.Event.ShowPaymentMethodError
            }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = createOrder(
                    isDelivery = state.isDelivery,
                    selectedUserAddress = state.selectedUserAddress,
                    selectedCafe = state.selectedCafe,
                    orderComment = state.comment,
                    deferredTime = when (state.deferredTime) {
                        CreateOrder.DeferredTime.Asap -> null
                        is CreateOrder.DeferredTime.Later -> state.deferredTime.time
                    },
                    timeZone = getSelectedCityTimeZone(),
                    paymentMethod = state.selectedPaymentMethod?.name?.name
                )
                if (orderCode == null) {
                    addEvent {
                        CreateOrder.Event.ShowSomethingWentWrongErrorEvent
                    }
                } else {
                    cartProductInteractor.removeAllProductsFromCart()
                    addEvent {
                        CreateOrder.Event.OrderCreatedEvent(code = orderCode.code)
                    }
                }
            } else {
                addEvent {
                    CreateOrder.Event.ShowUserUnauthorizedErrorEvent
                }
            }
        }
    }

    private suspend fun updateUserAddresses() {
        val userAddressList = getSelectableUserAddressList()
        setState {
            copy(userAddressList = userAddressList)
        }
        updateSelectedUserAddress()
    }

    private suspend fun updateCafeAddresses() {
        val cafeList = getSelectableCafeList()
        setState {
            copy(cafeList = cafeList)
        }
        updateSelectedCafe()
    }

    private suspend fun updatePaymentMethods() {
        val paymentMethodList = getSelectablePaymentMethodListUseCase()
        setState {
            copy(
                paymentMethodList = paymentMethodList,
                selectedPaymentMethod = paymentMethodList.find { it.isSelected }?.paymentMethod
            )
        }
    }

    private suspend fun updateSelectedUserAddress() {
        val userAddressList = getSelectableUserAddressList()
        setState {
            copy(userAddressList = userAddressList)
        }
        val selectableUserAddress = userAddressList.find { it.isSelected }

        setState {
            copy(selectedUserAddress = selectableUserAddress?.address)
        }
        if (selectableUserAddress != null) {
            setState {
                copy(isUserAddressError = false)
            }
        }
    }

    private suspend fun updateSelectedCafe() {
        val cafeList = getSelectableCafeList()
        setState {
            copy(cafeList = cafeList)
        }

        cafeList.find {
            it.isSelected
        }?.let { selectedCafe ->
            setState {
                copy(selectedCafe = selectedCafe.cafe)
            }
        }
    }

    private suspend fun updateCartTotal() {
        setState {
            copy(cartTotal = CreateOrder.CartTotal.Loading)
        }
        setState {
            val cartTotal = getCartTotal(isDelivery = isDelivery)
            copy(
                cartTotal = CreateOrder.CartTotal.Success(
                    discount = cartTotal.discount?.let { discount ->
                        "$discount$PERCENT"
                    },
                    deliveryCost = cartTotal.deliveryCost?.let { deliveryCost ->
                        "$deliveryCost $RUBLE_CURRENCY"
                    },
                    oldFinalCost = cartTotal.oldFinalCost?.let { oldFinalCost ->
                        "$oldFinalCost $RUBLE_CURRENCY"
                    },
                    newFinalCost = "${cartTotal.newFinalCost} $RUBLE_CURRENCY",
                )
            )
        }
    }

    private inline fun withLoading(crossinline block: suspend () -> Unit) {
        sharedScope.launchSafe(
            {
                mutableDataState.update { state ->
                    state.copy(isLoading = true)
                }
                block()
                setState {
                    copy(isLoading = false)
                }
                mutableDataState.update { state ->
                    state.copy(isLoading = false)
                }
            },
            onError = {
                addEvent {
                    CreateOrder.Event.ShowSomethingWentWrongErrorEvent
                }
            }
        )
    }
}
