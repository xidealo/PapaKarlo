package com.bunbeauty.shared.presentation.createorder

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.exeptions.NotAllowedTimeForOrderException
import com.bunbeauty.shared.domain.exeptions.OrderNotAvailableException
import com.bunbeauty.shared.domain.feature.address.GetCurrentUserAddressWithCityUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetAdditionalUtensilsUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetDeferredTimeHintUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetSelectableCafeListUseCase
import com.bunbeauty.shared.domain.feature.cafe.GetWorkloadCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.HasOpenedCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsDeliveryEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.cafe.IsPickupEnabledFromCafeUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.feature.motivation.GetMotivationUseCase
import com.bunbeauty.shared.domain.feature.order.CreateOrderUseCase
import com.bunbeauty.shared.domain.feature.order.ExtendedComment
import com.bunbeauty.shared.domain.feature.order.GetExtendedCommentUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectablePaymentMethodListUseCase
import com.bunbeauty.shared.domain.feature.payment.GetSelectedPaymentMethodUseCase
import com.bunbeauty.shared.domain.feature.payment.SavePaymentMethodUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.shared.domain.use_case.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.shared.domain.use_case.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.shared.domain.use_case.deferred_time.GetMinTimeUseCase
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.motivation.toMotivationData
import kotlinx.coroutines.Job
import kotlin.String

private const val DELIVERY_POSITION = 0
private const val CREATION_ORDER_VIEW_MODEL_TAG = "CreateOrderViewModel"

class CreateOrderViewModel(
    private val cartProductInteractor: ICartProductInteractor,
    private val cafeInteractor: ICafeInteractor,
    private val userInteractor: IUserInteractor,
    private val getSelectableUserAddressList: GetSelectableUserAddressListUseCase,
    private val getCurrentUserAddressWithCityUseCase: GetCurrentUserAddressWithCityUseCase,
    private val getSelectableCafeList: GetSelectableCafeListUseCase,
    private val getCartTotalFlowUseCase: GetCartTotalFlowUseCase,
    private val getMotivationUseCase: GetMotivationUseCase,
    private val getMinTime: GetMinTimeUseCase,
    private val createOrder: CreateOrderUseCase,
    private val getSelectedCityTimeZone: GetSelectedCityTimeZoneUseCase,
    private val saveSelectedUserAddress: SaveSelectedUserAddressUseCase,
    private val getSelectablePaymentMethodListUseCase: GetSelectablePaymentMethodListUseCase,
    private val savePaymentMethodUseCase: SavePaymentMethodUseCase,
    private val isDeliveryEnabledFromCafeUseCase: IsDeliveryEnabledFromCafeUseCase,
    private val isPickupEnabledFromCafeUseCase: IsPickupEnabledFromCafeUseCase,
    private val hasOpenedCafeUseCase: HasOpenedCafeUseCase,
    private val getWorkloadCafeUseCase: GetWorkloadCafeUseCase,
    private val getSelectedPaymentMethodUseCase: GetSelectedPaymentMethodUseCase,
    private val getExtendedCommentUseCase: GetExtendedCommentUseCase,
    private val getAdditionalUtensilsUseCase: GetAdditionalUtensilsUseCase,
    private val getDeferredTimeHintUseCase: GetDeferredTimeHintUseCase,
) : SharedStateViewModel<CreateOrder.DataState, CreateOrder.Action, CreateOrder.Event>(
    initDataState = CreateOrder.DataState(
        isDelivery = true,
        userAddressList = emptyList(),
        selectedUserAddressWithCity = null,
        isAddressErrorShown = CreateOrder.DataState.AddressErrorState.INIT,
        cafeList = emptyList(),
        selectedCafe = null,
        comment = "",
        deferredTime = CreateOrder.DeferredTime.Asap,
        paymentMethodList = emptyList(),
        selectedPaymentMethod = null,
        cartTotal = CreateOrder.CartTotal.Loading,
        isLoading = true,
        deliveryState = CreateOrder.DataState.DeliveryState.ENABLED,
        isPickupEnabled = true,
        hasOpenedCafe = true,
        workload = Cafe.Workload.LOW,
        additionalUtensils = false,
        additionalUtensilsCount = ""
    )
) {

    private var getCartTotalJob: Job? = null

    override fun reduce(action: CreateOrder.Action, dataState: CreateOrder.DataState) {
        when (action) {
            CreateOrder.Action.Init -> {
                loadData(isDelivery = dataState.isDelivery)
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
                changeUserAddress(
                    userAddressUuid = action.addressUuid,
                    isDelivery = dataState.isDelivery
                )
            }

            CreateOrder.Action.PickupAddressClick -> {
                cafeAddressClick()
            }

            CreateOrder.Action.HidePickupAddressList -> {
                hideCafeList()
            }

            is CreateOrder.Action.ChangePickupAddress -> {
                changeCafeAddress(
                    cafeUuid = action.addressUuid,
                    isDelivery = dataState.isDelivery
                )
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
                    changeFrom = action.changeFrom,
                    additionalUtensils = action.additionalUtensils
                )
            }

            CreateOrder.Action.Back -> addEvent {
                CreateOrder.Event.Back
            }

            is CreateOrder.Action.ChangeAdditionalUtensils -> changeAdditionalUtensilsCount(
                additionalUtensilsCount = action.additionalUtensilsCount
            )
        }
    }

    private fun loadData(isDelivery: Boolean) {
        withLoading {
            updateUserAddresses()

            updateCafeList()

            updatePaymentMethods()

            updateCartTotal()

            updateAdditionalUtensils(isDelivery = isDelivery)
        }
    }

    private fun changeMethod(position: Int) {
        val isDelivery = position == DELIVERY_POSITION
        setState {
            copy(
                isDelivery = isDelivery
            )
        }

        updateAdditionalUtensils(isDelivery = isDelivery)

        withLoading {
            updateCartTotal()
        }
    }

    private fun updateAdditionalUtensils(isDelivery: Boolean) {
        sharedScope.launchSafe(
            block = {
                setState {
                    copy(
                        additionalUtensils = getAdditionalUtensilsUseCase(
                            cafeUuid = if (isDelivery) {
                                selectedUserAddressWithCity?.userAddress?.cafeUuid.orEmpty()
                            } else {
                                selectedCafe?.uuid.orEmpty()
                            }
                        )
                    )
                }
            },
            onError = {
                // stub
            }
        )
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

    private fun changeUserAddress(userAddressUuid: String, isDelivery: Boolean) {
        setState {
            copy(isUserAddressListShown = false)
        }
        withLoading {
            saveSelectedUserAddress(userAddressUuid)
            updateSelectedUserAddress()
            updateAdditionalUtensils(isDelivery = isDelivery)
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

    private fun changeCafeAddress(cafeUuid: String, isDelivery: Boolean) {
        setState {
            copy(isCafeListShown = false)
        }
        withLoading {
            cafeInteractor.saveSelectedCafe(cafeUuid)
            updateCafeList()
            updateAdditionalUtensils(isDelivery = isDelivery)
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
                ),
                showTimePickerHint = getDeferredTimeHintUseCase(deferredTime),
                hasTimePickerError = false
            )
        }
    }

    private fun changeDeferredTimeToAsap() {
        setState {
            copy(
                isDeferredTimeShown = false,
                deferredTime = CreateOrder.DeferredTime.Asap,
                showTimePickerHint = false,
                hasTimePickerError = false
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
        additionalUtensils: String,
    ) {
        val state = mutableDataState.value

        val isDeliveryAddressNotSelected =
            state.isDelivery && (state.selectedUserAddressWithCity?.userAddress == null)

        if (isDeliveryAddressNotSelected) {
            setState {
                copy(isAddressErrorShown = CreateOrder.DataState.AddressErrorState.ERROR)
            }
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

        val isAdditionalUtensilsIncorrect = state.additionalUtensils &&
                state.additionalUtensilsCount.isEmpty()

        setState {
            copy(isAdditionalUtensilsErrorShown = isAdditionalUtensilsIncorrect)
        }

        if (isAdditionalUtensilsIncorrect) {
            addEvent {
                CreateOrder.Event.ShowAdditionalUtensilsError
            }
            return
        }

        if (state.hasTimePickerError) {
            addEvent {
                CreateOrder.Event.ShowTimePickerError
            }
            return
        }

        withLoading {
            if (userInteractor.isUserAuthorize()) {
                val orderCode = createOrder(
                    isDelivery = state.isDelivery,
                    selectedUserAddress = state.selectedUserAddressWithCity?.userAddress,
                    selectedCafe = state.selectedCafe,
                    orderComment = getExtendedCommentUseCase(
                        ExtendedComment(
                            comment = state.comment,
                            change = ExtendedComment.Change(
                                paymentByCash = state.paymentByCash,
                                withoutChangeChecked = state.withoutChangeChecked,
                                withoutChange = withoutChange,
                                changeFrom = changeFrom,
                                change = state.change?.toString().orEmpty()
                            ),
                            additionalUtensils = ExtendedComment.AdditionalUtensils(
                                isAdditionalUtensils = state.additionalUtensils,
                                count = state.additionalUtensilsCount,
                                name = additionalUtensils
                            )
                        )
                    ).takeIf { comment ->
                        comment.isNotBlank()
                    },
                    deferredTime = when (state.deferredTime) {
                        CreateOrder.DeferredTime.Asap -> null
                        is CreateOrder.DeferredTime.Later -> state.deferredTime.time
                    },
                    timeZone = getSelectedCityTimeZone(),
                    paymentMethod = state.selectedPaymentMethod.name.name
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
        updateSelectedUserAddress()
    }

    private suspend fun updatePaymentMethods() {
        val paymentMethodList = getSelectablePaymentMethodListUseCase()

        val selectedPaymentMethod =
            getSelectedPaymentMethodUseCase(selectablePaymentMethodList = paymentMethodList)

        setState {
            copy(
                paymentMethodList = paymentMethodList,
                selectedPaymentMethod = selectedPaymentMethod
            )
        }
        if (selectedPaymentMethod != null) {
            setState {
                copy(isPaymentMethodErrorShown = false)
            }
        }
    }

    private suspend fun updateSelectedUserAddress() {
        val userAddressWithCity = getCurrentUserAddressWithCityUseCase()
        val userAddressList = getSelectableUserAddressList()

        setState {
            copy(
                userAddressList = userAddressList,
                selectedUserAddressWithCity = userAddressWithCity,
                deliveryState = userAddressWithCity?.userAddress?.cafeUuid?.let { cafeUuid ->
                    if (isDeliveryEnabledFromCafeUseCase(cafeUuid = cafeUuid)) {
                        CreateOrder.DataState.DeliveryState.ENABLED
                    } else {
                        CreateOrder.DataState.DeliveryState.NOT_ENABLED
                    }
                } ?: CreateOrder.DataState.DeliveryState.NEED_ADDRESS,
                isAddressErrorShown = if (userAddressWithCity != null) {
                    CreateOrder.DataState.AddressErrorState.NO_ERROR
                } else {
                    isAddressErrorShown
                },
                workload = userAddressWithCity?.userAddress?.cafeUuid?.let { cafeUuid ->
                    getWorkloadCafeUseCase(cafeUuid = cafeUuid)
                } ?: Cafe.Workload.LOW,
                isLoadingSwitcher = false
            )
        }
    }

    private suspend fun updateCafeList() {
        val cafeList = getSelectableCafeList()
        setState {
            copy(cafeList = cafeList)
        }

        cafeList.find { cafe ->
            cafe.isSelected
        }?.let { selectedCafe ->
            setState {
                copy(
                    selectedCafe = selectedCafe.cafe,
                    isPickupEnabled = isPickupEnabledFromCafeUseCase(
                        cafeUuid = selectedCafe.cafe.uuid
                    ),
                    hasOpenedCafe = hasOpenedCafeUseCase(
                        cafeList = cafeList.map { selectableCafe ->
                            selectableCafe.cafe
                        }
                    )
                )
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
                            isDelivery = isDelivery
                        )
                    }
                }
            },
            onError = { error ->
                Logger.logE(CREATION_ORDER_VIEW_MODEL_TAG, error.stackTraceToString())
            }
        )
    }

    fun changeAdditionalUtensilsCount(
        additionalUtensilsCount: String,
    ) {
        setState {
            copy(
                additionalUtensilsCount = additionalUtensilsCount
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
            onError = { throwable ->
                when (throwable) {
                    is OrderNotAvailableException -> {
                        setState {
                            copy(
                                deliveryState = CreateOrder.DataState.DeliveryState.NOT_ENABLED
                            )
                        }
                        addEvent {
                            CreateOrder.Event.OrderNotAvailableErrorEvent
                        }
                    }

                    is NotAllowedTimeForOrderException -> {
                        setState {
                            copy(
                                hasTimePickerError = true,
                                showTimePickerHint = true
                            )
                        }
                        addEvent {
                            CreateOrder.Event.ShowTimePickerError
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
