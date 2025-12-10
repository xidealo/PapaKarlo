package com.bunbeauty.shared.ui.screen.createorder


import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.ui.screen.motivation.MotivationUi
import com.bunbeauty.shared.ui.screen.profile.screen.payment.PaymentMethodUI
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.StringResource

@Immutable
data class CreateOrderViewState(
    val createOrderType: CreateOrderType,
    val isAddressErrorShown: Boolean,
    val deferredTime: String,
    val deferredTimeStringId: StringResource,
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
    val isLoadingCreateOrder: Boolean,
    val isDeferredTimeShown: Boolean,
    val timePicker: TimePickerUI,
    val paymentMethodList: PaymentMethodListUI,
    val isOrderCreationEnabled: Boolean,
    val isLoadingSwitcher: Boolean,
    val additionalUtensils: Boolean,
    val additionalUtensilsName: String,
    val additionalUtensilsCount: String,
    val isAdditionalUtensilsErrorShown: Boolean,
) : BaseViewState {
    val isFieldsEnabled: Boolean = !isLoadingCreateOrder
    val switcherPosition = if (createOrderType is CreateOrderType.Delivery) 0 else 1

    @Immutable
    sealed interface CreateOrderType {
        @Immutable
        data class Pickup(
            val pickupAddress: String?,
            val pickupAddressList: PickupAddressListUI,
            val hasOpenedCafe: Boolean,
            val isEnabled: Boolean,
        ) : CreateOrderType

        @Immutable
        data class Delivery(
            val deliveryAddress: String?,
            val deliveryAddressList: DeliveryAddressListUI,
            val state: State,
            val workload: Workload,
        ) : CreateOrderType {
            enum class Workload {
                LOW,
                AVERAGE,
                HIGH,
            }

            enum class State {
                NOT_ENABLED,
                ENABLED,
                NEED_ADDRESS,
            }
        }
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
        val newFinalCost: String,
    ) : CartTotalUI
}

@Immutable
data class DeliveryAddressListUI(
    val isShown: Boolean,
    val addressList: ImmutableList<SelectableAddressUI>,
)

@Immutable
data class PickupAddressListUI(
    val isShown: Boolean,
    val addressList: ImmutableList<SelectableAddressUI>,
)

@Immutable
data class PaymentMethodListUI(
    val isShown: Boolean,
    val paymentMethodList: ImmutableList<SelectablePaymentMethodUI>,
)

@Immutable
data class TimePickerUI(
    val isShown: Boolean,
    val minTime: TimeUI,
    val initialTime: TimeUI,
)

@Immutable
data class TimeUI(
    val hours: Int,
    val minutes: Int,
)

@Immutable
data class SelectableAddressUI(
    val uuid: String,
    val address: String,
    val isSelected: Boolean,
    val isEnabled: Boolean,
)

@Immutable
data class SelectablePaymentMethodUI(
    val uuid: String,
    val name: String,
    val isSelected: Boolean,
)
