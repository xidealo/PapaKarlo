package com.bunbeauty.papakarlo.feature.createorder.mapper

import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.address.mapper.toAddressString
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CartTotalUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderViewState
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.DeliveryAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.PaymentMethodListUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.PickupAddressListUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.SelectableAddressUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.TimePickerUI
import com.bunbeauty.papakarlo.feature.deferredtime.toDeferredTimeString
import com.bunbeauty.papakarlo.feature.deferredtime.toTimeUI
import com.bunbeauty.papakarlo.feature.paymentmethod.toPaymentMethodUI
import com.bunbeauty.papakarlo.feature.paymentmethod.toSelectablePaymentMethodUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CreateOrder.DataState.toViewState(): CreateOrderViewState {
    return CreateOrderViewState(
        isDelivery = isDelivery,
        deliveryAddress = selectedUserAddress?.toAddressString(),
        pickupAddress = selectedCafe?.address,
        isAddressErrorShown = isDelivery && isAddressErrorShown,
        comment = comment,
        deferredTimeStringId = if (isDelivery) {
            R.string.delivery_time
        } else {
            R.string.pickup_time
        },
        deferredTime = deferredTime.toDeferredTimeString(),
        selectedPaymentMethod = selectedPaymentMethod?.toPaymentMethodUI(),
        isPaymentMethodErrorShown = isPaymentMethodErrorShown,
        cartTotal = when (cartTotal) {
            CreateOrder.CartTotal.Loading -> CartTotalUI.Loading
            is CreateOrder.CartTotal.Success -> {
                val successCartTotal = cartTotal as CreateOrder.CartTotal.Success
                CartTotalUI.Success(
                    discount = successCartTotal.discount,
                    deliveryCost = successCartTotal.deliveryCost,
                    oldFinalCost = successCartTotal.oldFinalCost,
                    newFinalCost = successCartTotal.newFinalCost
                )
            }
        },
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
        )
    )
}
