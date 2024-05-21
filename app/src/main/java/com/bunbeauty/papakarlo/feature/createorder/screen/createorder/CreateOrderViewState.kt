package com.bunbeauty.papakarlo.feature.createorder.screen.createorder

import androidx.annotation.StringRes
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.google.errorprone.annotations.Immutable

@Immutable
data class CreateOrderViewState(
    val isDelivery: Boolean,
    val deliveryAddress: String?,
    val pickupAddress: String?,
    val isAddressErrorShown: Boolean,
    val comment: String?,
    val deferredTime: String,
    @StringRes val deferredTimeHintStringId: Int,
    val selectedPaymentMethod: PaymentMethodUI?,
    val isPaymentMethodErrorShown: Boolean,
    val cartTotal: CartTotalUI,
    val isLoading: Boolean,
) : BaseViewState {

    val isFieldsEnabled: Boolean = !isLoading
    val switcherPosition = if (isDelivery) 0 else 1
}

@Immutable
sealed interface CartTotalUI {
    @Immutable
    data object Loading : CartTotalUI

    @Immutable
    data class Success(
        val discount: String?,
        val deliveryCost: String?,
        val oldFinalCost: String?,
        val newFinalCost: String,
    ) : CartTotalUI
}