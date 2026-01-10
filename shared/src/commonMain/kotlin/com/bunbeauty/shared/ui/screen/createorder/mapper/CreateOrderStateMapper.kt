package com.bunbeauty.shared.ui.screen.createorder.mapper

import androidx.compose.runtime.Composable
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.presentation.createorder.CreateOrder.DataState.AddressErrorState
import com.bunbeauty.address.ui.mapper.toAddressString
import com.bunbeauty.shared.ui.screen.createorder.CartTotalUI
import com.bunbeauty.shared.ui.screen.createorder.CreateOrderViewState
import com.bunbeauty.shared.ui.screen.createorder.DeliveryAddressListUI
import com.bunbeauty.shared.ui.screen.createorder.PaymentMethodListUI
import com.bunbeauty.shared.ui.screen.createorder.PickupAddressListUI
import com.bunbeauty.shared.ui.screen.createorder.SelectableAddressUI
import com.bunbeauty.shared.ui.screen.createorder.TimePickerUI
import com.bunbeauty.shared.ui.screen.createorder.deferredtime.toDeferredTimeString
import com.bunbeauty.shared.ui.screen.createorder.deferredtime.toTimeUI
import com.bunbeauty.shared.ui.motivation.MotivationUi
import com.bunbeauty.shared.ui.motivation.toMotivationUi
import com.bunbeauty.shared.ui.screen.createorder.paymentmethod.toPaymentMethodUI
import com.bunbeauty.shared.ui.screen.createorder.paymentmethod.toSelectablePaymentMethodUI
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.delivery_time
import papakarlo.designsystem.generated.resources.msg_additional_utensils_count
import papakarlo.designsystem.generated.resources.msg_change_from
import papakarlo.designsystem.generated.resources.msg_without_change
import papakarlo.designsystem.generated.resources.pickup_time

@Composable
fun CreateOrder.DataState.toViewState(): CreateOrderViewState {
    val cartTotalUI = cartTotal.toCartTotalUI()
    return CreateOrderViewState(
        createOrderType =
            if (isDelivery) {
                getCreateOrderTypeDelivery()
            } else {
                getCreateOrderTypePickup()
            },
        isAddressErrorShown = isDelivery && (isAddressErrorShown == AddressErrorState.ERROR),
        comment = comment,
        deferredTimeStringId =
            if (isDelivery) {
                Res.string.delivery_time
            } else {
                Res.string.pickup_time
            },
        hasTimePickerError = hasTimePickerError,
        showTimePickerHint = showTimePickerHint,
        deferredTime = deferredTime.toDeferredTimeString(),
        selectedPaymentMethod = selectedPaymentMethod?.toPaymentMethodUI(),
        isPaymentMethodErrorShown = isPaymentMethodErrorShown,
        showChange = paymentByCash,
        withoutChange = stringResource(Res.string.msg_without_change),
        changeFrom = stringResource(Res.string.msg_change_from),
        withoutChangeChecked = withoutChangeChecked,
        change = change?.toString().orEmpty(),
        isChangeErrorShown = isChangeErrorShown,
        isAdditionalUtensilsErrorShown = isAdditionalUtensilsErrorShown,
        cartTotal = cartTotalUI,
        isLoadingCreateOrder = isLoading,
        isDeferredTimeShown = isDeferredTimeShown,
        timePicker =
            TimePickerUI(
                isShown = isTimePickerShown,
                minTime = minDeferredTime.toTimeUI(),
                initialTime = initialDeferredTime.toTimeUI(),
            ),
        paymentMethodList =
            PaymentMethodListUI(
                isShown = isPaymentMethodListShown,
                paymentMethodList =
                    paymentMethodList
                        .map { selectablePaymentMethod ->
                            selectablePaymentMethod.toSelectablePaymentMethodUI()
                        }.toImmutableList(),
            ),
        isOrderCreationEnabled =
            if (isDelivery) {
                deliveryState == CreateOrder.DataState.DeliveryState.ENABLED &&
                    (cartTotalUI as? CartTotalUI.Success)?.motivation !is MotivationUi.MinOrderCost
            } else {
                isPickupEnabled
            },
        isLoadingSwitcher = isLoadingSwitcher,
        additionalUtensils = additionalUtensils,
        additionalUtensilsCount = additionalUtensilsCount,
        additionalUtensilsName = stringResource(Res.string.msg_additional_utensils_count),
    )
}

@Composable
private fun CreateOrder.DataState.getCreateOrderTypePickup() =
    CreateOrderViewState.CreateOrderType.Pickup(
        pickupAddress = selectedCafe?.address,
        pickupAddressList =
            PickupAddressListUI(
                isShown = isCafeListShown,
                addressList =
                    cafeList
                        .map { selectableCafe ->
                            SelectableAddressUI(
                                uuid = selectableCafe.cafe.uuid,
                                address = selectableCafe.cafe.address,
                                isSelected = selectableCafe.isSelected,
                                isEnabled = selectableCafe.canBePickup,
                            )
                        }.toImmutableList(),
            ),
        hasOpenedCafe = hasOpenedCafe,
        isEnabled = isPickupEnabled,
    )

@Composable
private fun CreateOrder.DataState.getCreateOrderTypeDelivery() =
    CreateOrderViewState.CreateOrderType.Delivery(
        deliveryAddress = selectedUserAddressWithCity?.toAddressString(),
        deliveryAddressList =
            DeliveryAddressListUI(
                isShown = isUserAddressListShown,
                addressList =
                    userAddressList
                        .map { selectableUserAddress ->
                            SelectableAddressUI(
                                uuid = selectableUserAddress.address.uuid,
                                address = selectableUserAddress.address.toAddressString(),
                                isSelected = selectableUserAddress.isSelected,
                                isEnabled = true,
                            )
                        }.toImmutableList(),
            ),
        state =
            when (deliveryState) {
                CreateOrder.DataState.DeliveryState.NOT_ENABLED -> {
                    CreateOrderViewState.CreateOrderType.Delivery.State.NOT_ENABLED
                }

                CreateOrder.DataState.DeliveryState.ENABLED -> {
                    CreateOrderViewState.CreateOrderType.Delivery.State.ENABLED
                }

                CreateOrder.DataState.DeliveryState.NEED_ADDRESS -> {
                    CreateOrderViewState.CreateOrderType.Delivery.State.NEED_ADDRESS
                }
            },
        workload =
            when (workload) {
                Cafe.Workload.LOW -> CreateOrderViewState.CreateOrderType.Delivery.Workload.LOW
                Cafe.Workload.AVERAGE -> CreateOrderViewState.CreateOrderType.Delivery.Workload.AVERAGE
                Cafe.Workload.HIGH -> CreateOrderViewState.CreateOrderType.Delivery.Workload.HIGH
            },
    )

private fun CreateOrder.CartTotal.toCartTotalUI(): CartTotalUI =
    when (this) {
        CreateOrder.CartTotal.Loading -> CartTotalUI.Loading
        is CreateOrder.CartTotal.Success -> {
            CartTotalUI.Success(
                motivation = motivation?.toMotivationUi(),
                discount = discount,
                deliveryCost = deliveryCost,
                oldFinalCost = oldFinalCost,
                newFinalCost = newFinalCost,
            )
        }
    }
