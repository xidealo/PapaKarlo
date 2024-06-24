package com.bunbeauty.shared.presentation.createorder

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
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
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.motivation.toMotivationData

private const val DELIVERY_POSITION = 0

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val getSelectableUserAddressList: GetSelectableUserAddressListUseCase,
    private val getSelectableCafeList: GetSelectableCafeListUseCase,
    private val getCartTotal: GetCartTotalUseCase,
    private val getMotivationUseCase: GetMotivationUseCase,
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
        isAddressErrorShown = false,
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

            CreateOrder.Action.DeliveryAddressClick -> {
                userAddressClick()
            }

            CreateOrder.Action.AddAddressClick -> {
                addAddressClick()
            }

            CreateOrder.Action.HideDeliveryAddressList -> {
                hideUserAddressList()
            }

            is CreateOrder.Action.ChangeDeliveryAddress -> {
                changeUserAddress(userAddressUuid = action.addressUuid)
            }

            CreateOrder.Action.PickupAddressClick -> {
                cafeAddressClick()
            }

            CreateOrder.Action.HidePickupAddressList -> {
                hideCafeList()
            }

            is CreateOrder.Action.ChangePickupAddress -> {
                changeCafeAddress(cafeUuid = action.addressUuid)
            }

            CreateOrder.Action.DeferredTimeClick -> {
                showDeferredTime()
            }

            CreateOrder.Action.HideDeferredTime -> {
                hideDeferredTime()
            }

            CreateOrder.Action.AsapClick -> {
                changeDeferredTimeToAsap()
            }

            CreateOrder.Action.PickTimeClick -> {
                showTimePicker()
            }

            CreateOrder.Action.HideTimePicker -> {
                hideTimePicker()
            }

            is CreateOrder.Action.ChangeDeferredTime -> {
                changeDeferredTime(deferredTime = action.time)
            }

            CreateOrder.Action.PaymentMethodClick -> {
                paymentMethodClick()
            }

            CreateOrder.Action.HidePaymentMethodList -> {
                hidePaymentMethodList()
            }

            is CreateOrder.Action.ChangePaymentMethod -> {
                changePaymentMethod(paymentMethodUuid = action.paymentMethodUuid)
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
        (position == DELIVERY_POSITION).let { isDelivery ->
            setState {
                copy(isDelivery = isDelivery)
            }
        }
        withLoading {
            updateCartTotal()
        }
    }

    private fun userAddressClick() {
        setState {
            copy(isUserAddressListShown = true)
        }
    }

    private fun addAddressClick() {
        addEvent {
            CreateOrder.Event.OpenCreateAddressEvent
        }
    }

    private fun hideUserAddressList() {
        setState {
            copy(isUserAddressListShown = false)
        }
    }

    private fun changeUserAddress(userAddressUuid: String) {
        setState {
            copy(isUserAddressListShown = false)
        }
        withLoading {
            saveSelectedUserAddress(userAddressUuid)
            updateSelectedUserAddress()
        }
    }

    private fun cafeAddressClick() {
        setState {
            copy(isCafeListShown = true)
        }
    }

    private fun hideCafeList() {
        setState {
            copy(isCafeListShown = false)
        }
    }

    private fun changeCafeAddress(cafeUuid: String) {
        setState {
            copy(isCafeListShown = false)
        }
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateSelectedCafe()
        }
    }

    private fun showDeferredTime() {
        setState {
            copy(isDeferredTimeShown = true)
        }
    }

    private fun hideDeferredTime() {
        setState {
            copy(isDeferredTimeShown = false)
        }
    }

    private fun showTimePicker() {
        sharedScope.launchSafe(
            block = {
                val timeZone = getSelectedCityTimeZone()
                val minDeferredTime = getMinTime(timeZone)

                setState {
                    copy(
                        isTimePickerShown = true,
                        minDeferredTime = getMinTime(timeZone),
                        initialDeferredTime = if (deferredTime is CreateOrder.DeferredTime.Later) {
                            deferredTime.time
                        } else {
                            minDeferredTime
                        },
                    )
                }
            },
            onError = {}
        )
    }

    private fun hideTimePicker() {
        setState {
            copy(isTimePickerShown = false)
        }
    }

    private fun changeDeferredTime(deferredTime: Time) {
        setState {
            copy(
                isDeferredTimeShown = false,
                isTimePickerShown = false,
                deferredTime = CreateOrder.DeferredTime.Later(
                    time = deferredTime
                )
            )
        }
    }

    private fun changeDeferredTimeToAsap() {
        setState {
            copy(
                isDeferredTimeShown = false,
                deferredTime = CreateOrder.DeferredTime.Asap
            )
        }
    }

    private fun paymentMethodClick() {
        setState {
            copy(
                isPaymentMethodListShown = true
            )
        }
    }

    private fun hidePaymentMethodList() {
        setState {
            copy(
                isPaymentMethodListShown = false
            )
        }
    }

    private fun changePaymentMethod(paymentMethodUuid: String) {
        setState {
            copy(
                isPaymentMethodListShown = false
            )
        }
        withLoading {
            savePaymentMethodUseCase(paymentMethodUuid)
            updatePaymentMethods()
        }
    }

    private fun onCommentChanged(comment: String) {
        setState {
            copy(comment = comment)
        }
    }

    private fun createClick() {
        val state = mutableDataState.value

        val isDeliveryAddressNotSelected = state.isDelivery && (state.selectedUserAddress == null)
        setState {
            copy(isAddressErrorShown = isDeliveryAddressNotSelected)
        }

        if (isDeliveryAddressNotSelected) {
            addEvent {
                CreateOrder.Event.ShowUserAddressError
            }
            return
        }

        val isPaymentMethodNotSelected = state.selectedPaymentMethod == null
        setState {
            copy(isPaymentMethodErrorShown = isPaymentMethodNotSelected)
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
        val selectedPaymentMethod = paymentMethodList.find { it.isSelected }?.paymentMethod
        setState {
            copy(
                paymentMethodList = paymentMethodList,
                selectedPaymentMethod = paymentMethodList.find { it.isSelected }?.paymentMethod
            )
        }
        if (selectedPaymentMethod != null) {
            setState {
                copy(isPaymentMethodErrorShown = false)
            }
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
                copy(isAddressErrorShown = false)
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
            val motivation = getMotivationUseCase(newTotalCost = cartTotal.newTotalCost)
            copy(
                cartTotal = CreateOrder.CartTotal.Success(
                    motivation = motivation?.toMotivationData(),
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
                setState {
                    copy(isLoading = true)
                }

                block()
                setState {
                    copy(isLoading = false)
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
