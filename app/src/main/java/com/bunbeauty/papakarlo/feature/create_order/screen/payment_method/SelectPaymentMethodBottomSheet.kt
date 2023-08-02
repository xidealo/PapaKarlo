package com.bunbeauty.papakarlo.feature.create_order.screen.payment_method

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SelectPaymentMethodBottomSheet : ComposeBottomSheet<SelectablePaymentMethodUI>() {

    private var paymentMethodList by argument<List<SelectablePaymentMethodUI>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            PaymentMethodListScreen(
                paymentMethodList = paymentMethodList,
                scrolledToTop = ::toggleDraggable,
                onPaymentMethodClicked = { addressItem ->
                    callback?.onResult(addressItem)
                    dismiss()
                },
            )
        }
    }

    companion object {
        private const val TAG = "SelectPaymentMethodBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            selectablePaymentMethodList: List<SelectablePaymentMethodUI>,
        ) = suspendCoroutine { continuation ->
            SelectPaymentMethodBottomSheet().apply {
                this.paymentMethodList = selectablePaymentMethodList
                callback = object : Callback<SelectablePaymentMethodUI> {
                    override fun onResult(result: SelectablePaymentMethodUI?) {
                        continuation.resume(result)
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun PaymentMethodListScreen(
    paymentMethodList: List<SelectablePaymentMethodUI>,
    scrolledToTop: (Boolean) -> Unit,
    onPaymentMethodClicked: (SelectablePaymentMethodUI) -> Unit,
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.pickup_payment_method,
        scrolledToTop = scrolledToTop,
    ) {
        itemsIndexed(paymentMethodList) { i, paymentItem ->
            AddressItem(
                modifier = Modifier.padding(
                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                ),
                address = paymentItem.paymentMethodUI.name,
                isClickable = true,
                elevated = false,
                isSelected = paymentItem.isSelected
            ) {
                onPaymentMethodClicked(paymentItem)
            }
        }
    }
}

@Preview
@Composable
private fun CafeAddressListScreenPreview() {
    FoodDeliveryTheme {
        PaymentMethodListScreen(
            paymentMethodList = listOf(
                SelectablePaymentMethodUI(
                    PaymentMethodUI(
                        uuid = "Карта",
                        name = "Карта",
                        value = PaymentMethodValueUI(
                            value = "asdsd",
                            valueToCopy = "asdsd",
                        ),
                    ),
                    isSelected = true
                ),
                SelectablePaymentMethodUI(
                    PaymentMethodUI(
                        uuid = "Нал",
                        name = "А нальчик можно?",
                        value = PaymentMethodValueUI(
                            value = "asdsd",
                            valueToCopy = "asdsd",
                        ),
                    ),
                    isSelected = true
                ),
            ),
            scrolledToTop = {},
            onPaymentMethodClicked = {},
        )
    }
}
