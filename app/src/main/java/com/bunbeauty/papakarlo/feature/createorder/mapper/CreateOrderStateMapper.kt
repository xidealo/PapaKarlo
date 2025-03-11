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
import com.bunbeauty.papakarlo.feature.motivation.toMotivationUi
import com.bunbeauty.papakarlo.feature.paymentmethod.toPaymentMethodUI
import com.bunbeauty.papakarlo.feature.paymentmethod.toSelectablePaymentMethodUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import com.bunbeauty.shared.presentation.createorder.CreateOrder.DataState.AddressErrorState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CreateOrder.DataState.toViewState(): CreateOrderViewState {
    return CreateOrderViewState(
        workType = when (workType) {
            CreateOrder.DataState.WorkType.DELIVERY -> CreateOrderViewState.WorkType.Delivery
            CreateOrder.DataState.WorkType.CLOSED_DELIVERY -> CreateOrderViewState.WorkType.Delivery

            CreateOrder.DataState.WorkType.PICKUP -> CreateOrderViewState.WorkType.Pickup
            CreateOrder.DataState.WorkType.DELIVERY_AND_PICKUP -> CreateOrderViewState.WorkType.DeliveryAndPickup(
                isDelivery = isDelivery
            )

            CreateOrder.DataState.WorkType.CLOSED -> CreateOrderViewState.WorkType.DeliveryAndPickup(
                isDelivery = isDelivery
            )
        },
        deliveryAddress = selectedUserAddress?.toAddressString(),
        pickupAddress = selectedCafe?.address,
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
        change = change?.toString() ?: "",
        isChangeErrorShown = isChangeErrorShown,
        cartTotal = cartTotal.toCartTotalUI(),
        isLoading = isLoading,
        deliveryAddressList = DeliveryAddressListUI(
            isShown = isUserAddressListShown,
            addressList = userAddressList.map { selectableUserAddress ->
                SelectableAddressUI(
                    uuid = selectableUserAddress.address.uuid,
                    address = selectableUserAddress.address.toAddressString(),
                    isSelected = selectableUserAddress.isSelected
                )
            }.toImmutableList()
        ),
        pickupAddressList = PickupAddressListUI(
            isShown = isCafeListShown,
            addressList = cafeList.map { selectableCafe ->
                SelectableAddressUI(
                    uuid = selectableCafe.cafe.uuid,
                    address = selectableCafe.cafe.address,
                    isSelected = selectableCafe.isSelected
                )
            }.toImmutableList()
        ),
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
        isOrderCreationEnabled = isOrderCreationEnabled,
        switcherOptionList = getSwitcherOptionList(workType),
        isLoadingSwitcher = isLoadingSwitcher
    )
}

@Composable
private fun getSwitcherOptionList(workType: CreateOrder.DataState.WorkType): ImmutableList<String> {
    return when (workType) {
        CreateOrder.DataState.WorkType.DELIVERY -> persistentListOf(
            stringResource(R.string.action_create_order_delivery)
        )

        CreateOrder.DataState.WorkType.PICKUP -> persistentListOf(
            stringResource(R.string.action_create_order_pickup)
        )

        CreateOrder.DataState.WorkType.DELIVERY_AND_PICKUP -> persistentListOf(
            stringResource(R.string.action_create_order_delivery),
            stringResource(R.string.action_create_order_pickup)
        )

        CreateOrder.DataState.WorkType.CLOSED -> persistentListOf(
            stringResource(R.string.action_create_order_delivery),
            stringResource(R.string.action_create_order_pickup)
        )

        CreateOrder.DataState.WorkType.CLOSED_DELIVERY -> persistentListOf(
            stringResource(R.string.action_create_order_delivery)
        )
    }
}

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
