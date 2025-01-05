package com.bunbeauty.shared.presentation.createorder

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.exeptions.OrderNotAvailableException
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.feature.orderavailable.GetWorkInfoUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.motivation.MotivationData
import com.bunbeauty.shared.presentation.motivation.toMotivationData
import kotlinx.coroutines.Job

private const val DELIVERY_POSITION = 0
private const val CREATION_ORDER_VIEW_MODEL_TAG = "CreateOrderViewModel"

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val getSelectableUserAddressList: GetSelectableUserAddressListUseCase,
    private val getSelectableCafeList: GetSelectableCafeListUseCase,
    private val getCartTotalFlowUseCase: GetCartTotalFlowUseCase,
    private val getMotivationUseCase: GetMotivationUseCase,
    private val getMinTime: GetMinTimeUseCase,
    private val createOrder: CreateOrderUseCase,
    private val getSelectedCityTimeZone: GetSelectedCityTimeZoneUseCase,
    private val saveSelectedUserAddress: SaveSelectedUserAddressUseCase,
    private val getSelectablePaymentMethodListUseCase: GetSelectablePaymentMethodListUseCase,
    private val savePaymentMethodUseCase: SavePaymentMethodUseCase,
    private val getWorkInfoUseCase: GetWorkInfoUseCase,
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
        workType = CreateOrder.DataState.WorkType.DELIVERY_AND_PICKUP
    )
) {

    private var getCartTotalJob: Job? = null

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

            is CreateOrder.Action.ChangeWithoutChangeChecked -> {
                changeWithoutChangeChecked()
            }

            is CreateOrder.Action.ChangeChange -> {
                changeChange(change = action.change)
            }

            is CreateOrder.Action.ChangeComment -> {
                changeComment(comment = action.comment)
            }

            is CreateOrder.Action.CreateClick -> {
                createClick(
                    withoutChange = action.withoutChange,
                    changeFrom = action.changeFrom
                )
            }

            CreateOrder.Action.Back -> addEvent {
                CreateOrder.Event.Back
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
                        }
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

    private fun changeWithoutChangeChecked() {
        setState {
            copy(withoutChangeChecked = !withoutChangeChecked)
        }
    }

    private fun changeChange(change: String) {
        setState {
            copy(change = change.toIntOrNull())
        }
    }

    private fun changeComment(comment: String) {
        setState {
            copy(comment = comment)
        }
    }

    private fun createClick(
        withoutChange: String,
        changeFrom: String,
    ) {
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

        val newFinalCost =
            (state.cartTotal as? CreateOrder.CartTotal.Success)?.newFinalCostValue ?: 0
        val isChangeLessThenCost = (state.change ?: 0) < newFinalCost
        val isChangeIncorrect = state.paymentByCash &&
                !state.withoutChangeChecked &&
                isChangeLessThenCost
        setState {
            copy(isChangeErrorShown = isChangeIncorrect)
        }
        if (isChangeIncorrect) {
            addEvent {
                CreateOrder.Event.ShowChangeError
            }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = createOrder(
                    isDelivery = state.isDelivery,
                    selectedUserAddress = state.selectedUserAddress,
                    selectedCafe = state.selectedCafe,
                    orderComment = getExtendedComment(
                        state = state,
                        withoutChange = withoutChange,
                        changeFrom = changeFrom
                    ).takeIf { comment ->
                        comment.isNotBlank()
                    },
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
        getCartTotalJob?.cancel()
        getCartTotalJob = sharedScope.launchSafe(
            block = {
                val isDelivery = mutableDataState.value.isDelivery
                getCartTotalFlowUseCase(isDelivery = isDelivery).collect { cartTotal ->
                    val motivation = getMotivationUseCase(
                        newTotalCost = cartTotal.newTotalCost,
                        isDelivery = isDelivery
                    )
                    val motivationData = motivation?.toMotivationData()
                    val workInfoType =
                        getWorkInfoUseCase()?.workInfoType
                            ?: WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP

                    setState {
                        copy(
                            cartTotal = CreateOrder.CartTotal.Success(
                                motivation = motivationData,
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
                                newFinalCostValue = cartTotal.newFinalCost
                            ),
                            workType = getWorkType(
                                motivationData = motivationData,
                                workType = workInfoType
                            ),
                            isDelivery = when (workInfoType) {
                                WorkInfo.WorkInfoType.DELIVERY -> true
                                WorkInfo.WorkInfoType.PICKUP -> false
                                WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP -> isDelivery
                                WorkInfo.WorkInfoType.CLOSED -> true
                            },
                            isLoadingSwitcher = false
                        )
                    }
                }
            },
            onError = { error ->
                Logger.logE(CREATION_ORDER_VIEW_MODEL_TAG, error.stackTraceToString())
            }
        )
    }

    private fun getWorkType(
        motivationData: MotivationData?,
        workType: WorkInfo.WorkInfoType,
    ): CreateOrder.DataState.WorkType {
        return if (motivationData is MotivationData.MinOrderCost) {
            CreateOrder.DataState.WorkType.CLOSED
        } else {
            when (workType) {
                WorkInfo.WorkInfoType.DELIVERY -> CreateOrder.DataState.WorkType.DELIVERY
                WorkInfo.WorkInfoType.PICKUP -> CreateOrder.DataState.WorkType.PICKUP
                WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP -> CreateOrder.DataState.WorkType.DELIVERY_AND_PICKUP
                WorkInfo.WorkInfoType.CLOSED -> CreateOrder.DataState.WorkType.CLOSED
            }
        }
    }

    private fun getExtendedComment(
        state: CreateOrder.DataState,
        withoutChange: String,
        changeFrom: String,
    ): String {
        return buildString {
            state.comment.takeIf { comment ->
                comment.isNotBlank()
            }?.let { comment ->
                append("$comment ")
            }
            if (state.paymentByCash) {
                append("(")
                if (state.withoutChangeChecked) {
                    append(withoutChange)
                } else {
                    append("$changeFrom ${state.change} $RUBLE_CURRENCY")
                }
                append(")")
            }
        }.trim()
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
            onError = { throwable ->
                when (throwable) {
                    is OrderNotAvailableException -> {
                        addEvent {
                            CreateOrder.Event.OrderNotAvailableErrorEvent
                        }
                    }

                    else -> addEvent {
                        CreateOrder.Event.ShowSomethingWentWrongErrorEvent
                    }
                }
                setState {
                    copy(isLoading = false)
                }
            }
        )
    }
}
