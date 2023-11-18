package com.bunbeauty.papakarlo.feature.profile.screen.payment

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliverySnackbarBox
import com.bunbeauty.papakarlo.common.ui.element.card.SimpleCard
import com.bunbeauty.papakarlo.common.ui.element.card.StartIconTextCard
import com.bunbeauty.papakarlo.common.ui.element.rememberFoodDeliverySnackbarState
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

class PaymentBottomSheet : ComposeBottomSheet<Any>() {

    private var paymentMethodsArgument by argument<PaymentMethodsArgument>()

    @Composable
    override fun Content() {
        val snackbarState = rememberFoodDeliverySnackbarState(
            stringResource(R.string.common_copied)
        )

        FoodDeliverySnackbarBox(snackbarState) {
            PaymentScreen(
                paymentMethodList = paymentMethodsArgument.paymentMethodList,
                onCopyClick = { nameWithCopyableValue ->
                    nameWithCopyableValue.value?.let { value ->
                        copyToBuffer(
                            label = nameWithCopyableValue.name,
                            text = value.valueToCopy
                        )
                        snackbarState.show()
                    }
                }
            )
        }
    }

    private fun copyToBuffer(label: String, text: String) {
        val clipboard =
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
    }

    companion object {
        private const val TAG = "PaymentBottomSheet"

        fun show(
            fragmentManager: FragmentManager,
            paymentMethodsArgument: PaymentMethodsArgument
        ) = PaymentBottomSheet().apply {
            this.paymentMethodsArgument = paymentMethodsArgument
            show(fragmentManager, TAG)
        }
    }
}

@Composable
private fun PaymentScreen(
    paymentMethodList: List<PaymentMethodUI>,
    onCopyClick: (PaymentMethodUI) -> Unit
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_payment) {
        Text(
            text = stringResource(R.string.msg_payment_methods),
            style = FoodDeliveryTheme.typography.bodyMedium,
            color = FoodDeliveryTheme.colors.mainColors.onSurface
        )
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            paymentMethodList.forEach { paymentMethod ->
                if (paymentMethod.value == null) {
                    SimpleCard(
                        text = paymentMethod.name,
                        elevated = false,
                        clickable = false
                    )
                } else {
                    StartIconTextCard(
                        hint = paymentMethod.name,
                        label = paymentMethod.value.value,
                        iconId = R.drawable.ic_copy,
                        elevated = false,
                        onClick = {
                            onCopyClick(paymentMethod)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    FoodDeliveryTheme {
        PaymentScreen(
            listOf(
                PaymentMethodUI(
                    uuid = "",
                    name = "Наличные",
                    value = null
                ),
                PaymentMethodUI(
                    uuid = "",
                    name = "Картой при получении",
                    value = null
                ),
                PaymentMethodUI(
                    uuid = "",
                    name = "Перевод по номеру карты",
                    value = PaymentMethodValueUI(
                        value = "1234 1234 1234 1234",
                        valueToCopy = "1234123412341234"
                    )
                ),
                PaymentMethodUI(
                    uuid = "",
                    name = "Перевод по номеру телефона",
                    value = PaymentMethodValueUI(
                        value = "+7 (900) 900-90-90",
                        valueToCopy = "+79009009090"
                    )
                )
            ),
            onCopyClick = {}
        )
    }
}
