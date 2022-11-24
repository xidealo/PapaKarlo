package com.bunbeauty.papakarlo.feature.profile.screen.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.element.card.IconCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetPaymentBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.shared.Constants.CARD_NUMBER_LABEL
import com.bunbeauty.shared.Constants.PHONE_NUMBER_LABEL
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_payment) {

    override val viewModel: PaymentViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetPaymentCvMain.compose {
            PaymentScreen()
        }
    }

    private fun copyToBuffer(label: String, data: String) {
        val clipboard =
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, data)
        clipboard?.setPrimaryClip(clip)
    }

    @Composable
    private fun PaymentScreen() {
        val payment by viewModel.payment.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Title(textStringId = R.string.title_payment)
            Text(
                text = stringResource(R.string.msg_payment_description),
                style = FoodDeliveryTheme.typography.body1
            )
            if (payment == null) {
                CircularProgressBar(
                    modifier = Modifier
                        .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                        .align(CenterHorizontally)
                )
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    payment?.cardNumber?.let { cardNumber ->
                        IconCard(
                            modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                            label = cardNumber,
                            iconId = R.drawable.ic_copy,
                            iconDescriptionStringId = R.string.description_payment_copy,
                            isClickable = true
                        ) {
                            copyToBuffer(CARD_NUMBER_LABEL, cardNumber)
                            viewModel.showMessage(
                                requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied),
                                true
                            )
                        }
                    }
                    payment?.phoneNumber?.let { phoneNumber ->
                        IconCard(
                            modifier = Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.smallSpace,
                                bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                            ),
                            label = phoneNumber,
                            iconId = R.drawable.ic_copy,
                            iconDescriptionStringId = R.string.description_payment_copy,
                            isClickable = true
                        ) {
                            copyToBuffer(PHONE_NUMBER_LABEL, phoneNumber)
                            viewModel.showMessage(
                                requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied),
                                true
                            )
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun PaymentScreenPreview() {
        PaymentScreen()
    }
}
