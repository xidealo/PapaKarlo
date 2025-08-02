package com.bunbeauty.papakarlo.feature.createorder.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.address.mapper.toAddressString
import com.bunbeauty.papakarlo.feature.createorder.CartTotalUI
import com.bunbeauty.papakarlo.feature.createorder.CreateOrderViewState
import com.bunbeauty.papakarlo.feature.createorder.DeliveryAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.PaymentMethodListUI
import com.bunbeauty.papakarlo.feature.createorder.PickupAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.SelectableAddressUI
import com.bunbeauty.papakarlo.feature.createorder.TimePickerUI
import com.bunbeauty.papakarlo.feature.deferredtime.toDeferredTimeString
import com.bunbeauty.papakarlo.feature.deferredtime.toTimeUI
import com.bunbeauty.papakarlo.feature.motivation.MotivationUi
import com.bunbeauty.papakarlo.feature.motivation.toMotivationUi
import com.bunbeauty.papakarlo.feature.paymentmethod.toPaymentMethodUI
import com.bunbeauty.papakarlo.feature.paymentmethod.toSelectablePaymentMethodUI
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.presentation.createorder.CreateOrder.DataState.AddressErrorState
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CreateOrder.DataState.toViewState(): CreateOrderViewState {
    val cartTotalUI = cartTotal.toCartTotalUI()
    return CreateOrderViewState(
        createOrderType = if (isDelivery) {
            getCreateOrderTypeDelivery()
        } else {
            getCreateOrderTypePickup()
        },
        isAddressErrorShown = isDelivery && (isAddressErrorShown == AddressErrorState.ERROR),
        comment = comment,
        deferredTimeStringId = if (isDelivery) {
            R.string.delivery_time
        } else {
            R.string.pickup_time
        },
        deferredTime = deferredTime.toDeferredTimeString(),
        selectedPaymentMethod = selectedPaymentMethod?.toPaymentMethodUI(),
        isPaymentMethodErrorShown = isPaymentMethodErrorShown,
        showChange = paymentByCash,
        withoutChange = stringResource(R.string.msg_without_change),
        changeFrom = stringResource(R.string.msg_change_from),
        withoutChangeChecked = withoutChangeChecked,
        change = change?.toString().orEmpty(),
        isChangeErrorShown = isChangeErrorShown,
        cartTotal = cartTotalUI,
        isLoadingCreateOrder = isLoading,
        isDeferredTimeShown = isDeferredTimeShown,
        timePicker = TimePickerUI(
            isShown = isTimePickerShown,
            minTime = minDeferredTime.toTimeUI(),
            initialTime = initialDeferredTime.toTimeUI()
        ),
        paymentMethodList = PaymentMethodListUI(
            isShown = isPaymentMethodListShown,
            paymentMethodList = paymentMethodList.map { selectablePaymentMethod ->
                selectablePaymentMethod.toSelectablePaymentMethodUI()
            }.toImmutableList()
        ),
        isOrderCreationEnabled = if (isDelivery) {
            deliveryState == CreateOrder.DataState.DeliveryState.ENABLED &&
                    (cartTotalUI as? CartTotalUI.Success)?.motivation !is MotivationUi.MinOrderCost
        } else {
            isPickupEnabled
        },
        isLoadingSwitcher = isLoadingSwitcher,
        additionalUtensils = additionalUtensils,
        additionalUtensilsCount = additionalUtensilsCount
    )
}

@Composable
private fun CreateOrder.DataState.getCreateOrderTypePickup() =
    CreateOrderViewState.CreateOrderType.Pickup(
        pickupAddress = selectedCafe?.address,
        pickupAddressList = PickupAddressListUI(
            isShown = isCafeListShown,
            addressList = cafeList.map { selectableCafe ->
                SelectableAddressUI(
                    uuid = selectableCafe.cafe.uuid,
                    address = selectableCafe.cafe.address,
                    isSelected = selectableCafe.isSelected,
                    isEnabled = selectableCafe.canBePickup
                )
            }.toImmutableList()
        ),
        hasOpenedCafe = hasOpenedCafe,
        isEnabled = isPickupEnabled
    )

@Composable
private fun CreateOrder.DataState.getCreateOrderTypeDelivery() =
    CreateOrderViewState.CreateOrderType.Delivery(
        deliveryAddress = selectedUserAddressWithCity?.toAddressString(),
        deliveryAddressList = DeliveryAddressListUI(
            isShown = isUserAddressListShown,
            addressList = userAddressList.map { selectableUserAddress ->
                SelectableAddressUI(
                    uuid = selectableUserAddress.address.uuid,
                    address = selectableUserAddress.address.toAddressString(),
                    isSelected = selectableUserAddress.isSelected,
                    isEnabled = true
                )
            }.toImmutableList()
        ),
        state = when (deliveryState) {
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
        workload = when (workload) {
            Cafe.Workload.LOW -> CreateOrderViewState.CreateOrderType.Delivery.Workload.LOW
            Cafe.Workload.AVERAGE -> CreateOrderViewState.CreateOrderType.Delivery.Workload.AVERAGE
            Cafe.Workload.HIGH -> CreateOrderViewState.CreateOrderType.Delivery.Workload.HIGH
        },
    )

private fun CreateOrder.CartTotal.toCartTotalUI(): CartTotalUI {
    return when (this) {
        CreateOrder.CartTotal.Loading -> CartTotalUI.Loading
        is CreateOrder.CartTotal.Success -> {
            CartTotalUI.Success(
                motivation = motivation?.toMotivationUi(),
                discount = discount,
                deliveryCost = deliveryCost,
                oldFinalCost = oldFinalCost,
                newFinalCost = newFinalCost
            )
        }
    }
}
