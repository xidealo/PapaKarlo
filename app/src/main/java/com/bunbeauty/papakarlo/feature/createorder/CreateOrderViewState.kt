package com.bunbeauty.papakarlo.feature.createorder

import androidx.annotation.StringRes
import com.bunbeauty.papakarlo.feature.motivation.MotivationUi
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.google.errorprone.annotations.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class CreateOrderViewState(
    val workType: WorkType,
    val deliveryAddress: String?,
    val pickupAddress: String?,
    val isAddressErrorShown: Boolean,
    val deferredTime: String,
    @StringRes val deferredTimeStringId: Int,
    val selectedPaymentMethod: PaymentMethodUI?,
    val isPaymentMethodErrorShown: Boolean,
    val showChange: Boolean,
    val withoutChange: String,
    val changeFrom: String,
    val withoutChangeChecked: Boolean,
    val change: String,
    val isChangeErrorShown: Boolean,
    val comment: String,
    val cartTotal: CartTotalUI,
    val isLoading: Boolean,

    val deliveryAddressList: DeliveryAddressListUI,
    val pickupAddressList: PickupAddressListUI,
    val isDeferredTimeShown: Boolean,
    val timePicker: TimePickerUI,
    val paymentMethodList: PaymentMethodListUI,
    val isOrderCreationEnabled: Boolean,
    val switcherOptionList: ImmutableList<String>,
    val isLoadingSwitcher: Boolean
) : BaseViewState {

    val isFieldsEnabled: Boolean = !isLoading
    val switcherPosition = when (workType) {
        WorkType.Delivery -> 0
        is WorkType.DeliveryAndPickup -> if (workType.isDelivery) 0 else 1
        WorkType.Pickup -> 0
    }

    @Immutable
    sealed interface WorkType {
        @Immutable
        data object Pickup : WorkType

        @Immutable
        data object Delivery : WorkType

        @Immutable
        data class DeliveryAndPickup(val isDelivery: Boolean) : WorkType
    }
}

@Immutable
sealed interface CartTotalUI {
    @Immutable
    data object Loading : CartTotalUI

    @Immutable
    data class Success(
        val motivation: MotivationUi?,
        val discount: String?,
        val deliveryCost: String?,
        val oldFinalCost: String?,
        val newFinalCost: String
    ) : CartTotalUI
}

@Immutable
data class DeliveryAddressListUI(
    val isShown: Boolean,
    val addressList: ImmutableList<SelectableAddressUI>
)

@Immutable
data class PickupAddressListUI(
    val isShown: Boolean,
    val addressList: ImmutableList<SelectableAddressUI>
)

@Immutable
data class PaymentMethodListUI(
    val isShown: Boolean,
    val paymentMethodList: ImmutableList<SelectablePaymentMethodUI>
)

@Immutable
data class TimePickerUI(
    val isShown: Boolean,
    val minTime: TimeUI,
    val initialTime: TimeUI
)

@Immutable
data class TimeUI(
    val hours: Int,
    val minutes: Int
)

@Immutable
data class SelectableAddressUI(
    val uuid: String,
    val address: String,
    val isSelected: Boolean
)

@Immutable
data class SelectablePaymentMethodUI(
    val uuid: String,
    val name: String,
    val isSelected: Boolean
)
