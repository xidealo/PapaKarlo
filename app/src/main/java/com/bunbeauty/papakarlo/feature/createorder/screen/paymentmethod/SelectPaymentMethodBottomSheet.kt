package com.bunbeauty.papakarlo.feature.createorder.screen.paymentmethod

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.address.ui.SelectableItemView
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodUI
import com.bunbeauty.papakarlo.feature.profile.screen.payment.PaymentMethodValueUI
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SelectPaymentMethodBottomSheet : ComposeBottomSheet<SelectablePaymentMethodUI>() {

    private var paymentMethodList by argument<List<SelectablePaymentMethodUI>>()

    @Composable
    override fun Content() {
        PaymentMethodListScreen(
            paymentMethodList = paymentMethodList,
            scrolledToTop = ::toggleDraggable,
            onPaymentMethodClicked = { paymentMethodUI ->
                callback?.onResult(paymentMethodUI)
                dismiss()
            }
        )
    }

    companion object {
        private const val TAG = "SelectPaymentMethodBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            paymentMethodList: List<SelectablePaymentMethodUI>
        ): SelectablePaymentMethodUI? {
            return suspendCoroutine { continuation ->
                SelectPaymentMethodBottomSheet().apply {
                    this.paymentMethodList = paymentMethodList
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
}

@Composable
private fun PaymentMethodListScreen(
    paymentMethodList: List<SelectablePaymentMethodUI>,
    scrolledToTop: (Boolean) -> Unit,
    onPaymentMethodClicked: (SelectablePaymentMethodUI) -> Unit
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.pickup_payment_method,
        scrolledToTop = scrolledToTop
    ) {
        items(paymentMethodList) { paymentItem ->
            SelectableItemView(
                title = paymentItem.paymentMethodUI.name,
                isClickable = true,
                elevated = false,
                isSelected = paymentItem.isSelected,
                onClick = {
                    onPaymentMethodClicked(paymentItem)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PaymentMethodListScreenPreview() {
    FoodDeliveryTheme {
        PaymentMethodListScreen(
            paymentMethodList = listOf(
                SelectablePaymentMethodUI(
                    PaymentMethodUI(
                        uuid = "Карта",
                        name = "Карта",
                        value = PaymentMethodValueUI(
                            value = "asdsd",
                            valueToCopy = "asdsd"
                        )
                    ),
                    isSelected = false
                ),
                SelectablePaymentMethodUI(
                    PaymentMethodUI(
                        uuid = "Нал",
                        name = "А нальчик можно?",
                        value = PaymentMethodValueUI(
                            value = "asdsd",
                            valueToCopy = "asdsd"
                        )
                    ),
                    isSelected = true
                )
            ),
            scrolledToTop = {},
            onPaymentMethodClicked = {}
        )
    }
}
