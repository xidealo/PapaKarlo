package com.bunbeauty.papakarlo.feature.createorder.mapper

import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.address.mapper.toAddressString
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CartTotalUI
import com.bunbeauty.papakarlo.feature.createorder.screen.createorder.CreateOrderViewState
import com.bunbeauty.papakarlo.feature.deferredtime.toDeferredTimeString
import com.bunbeauty.papakarlo.feature.paymentmethod.toPaymentMethodUI
import com.bunbeauty.shared.presentation.createorder.CreateOrder

@Composable
fun CreateOrder.DataState.toViewState(): CreateOrderViewState {
    return CreateOrderViewState(
        isDelivery = isDelivery,
        deliveryAddress = selectedUserAddress?.toAddressString(),
        pickupAddress = selectedCafe?.address,
        isAddressErrorShown = isDelivery && isUserAddressError,
        comment = comment,
        deferredTimeHintStringId = if (isDelivery) {
            R.string.delivery_time
        } else {
            R.string.pickup_time
        },
        deferredTime = deferredTime.toDeferredTimeString(),
        selectedPaymentMethod = selectedPaymentMethod?.toPaymentMethodUI(),
        isPaymentMethodErrorShown = isPaymentMethodError,
        cartTotal = when (cartTotal) {
            CreateOrder.CartTotal.Loading -> CartTotalUI.Loading
            is CreateOrder.CartTotal.Success -> {
                val successCartTotal = cartTotal as CreateOrder.CartTotal.Success
                CartTotalUI.Success(
                    discount = successCartTotal.discount,
                    deliveryCost = successCartTotal.deliveryCost,
                    oldFinalCost = successCartTotal.oldFinalCost,
                    newFinalCost = successCartTotal.newFinalCost,
                )
            }
        },
        isLoading = isLoading,
    )
}
