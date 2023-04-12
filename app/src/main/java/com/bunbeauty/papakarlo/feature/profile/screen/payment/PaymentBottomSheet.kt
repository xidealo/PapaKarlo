package com.bunbeauty.papakarlo.feature.profile.screen.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.card.StartIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.Constants.CARD_NUMBER_LABEL
import com.bunbeauty.shared.Constants.PHONE_NUMBER_LABEL
import com.bunbeauty.shared.domain.model.Payment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_compose) {

    override val viewModel: PaymentViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetComposeBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val paymentState by viewModel.paymentState.collectAsStateWithLifecycle()

            PaymentScreen(paymentState)
        }
    }

    private fun copyToBuffer(label: String, data: String) {
        val clipboard =
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, data)
        clipboard?.setPrimaryClip(clip)
    }

    @Composable
    private fun PaymentScreen(paymentState: PaymentState) {
        FoodDeliveryBottomSheet(titleStringId = R.string.title_payment) {
            Text(
                text = paymentState.paymentInfo,
                style = FoodDeliveryTheme.typography.bodyMedium
            )
            if (paymentState.payment == null) {
                CircularProgressBar(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(CenterHorizontally)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    paymentState.payment.cardNumber?.let { cardNumber ->
                        StartIconCard(
                            elevated = false,
                            label = cardNumber,
                            iconId = R.drawable.ic_copy,
                            onClick = {
                                copyToBuffer(CARD_NUMBER_LABEL, cardNumber)
                                viewModel.showMessage(
                                    requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied),
                                    true
                                )
                            }
                        )
                    }
                    paymentState.payment.phoneNumber?.let { phoneNumber ->
                        StartIconCard(
                            elevated = false,
                            label = phoneNumber,
                            iconId = R.drawable.ic_copy,
                            onClick = {
                                copyToBuffer(PHONE_NUMBER_LABEL, phoneNumber)
                                viewModel.showMessage(
                                    requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied),
                                    true
                                )
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
                PaymentState(
                    paymentInfo = "Оплатить заказ можно наличными или через терминал при получении",
                    payment = Payment(
                        phoneNumber = "+7(999) 999-99-99",
                        cardNumber = "1234 1234 1234 1234 1234"
                    )
                )

            )
        }
    }
}
