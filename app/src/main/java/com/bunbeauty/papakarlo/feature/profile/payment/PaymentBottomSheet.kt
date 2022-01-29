package com.bunbeauty.papakarlo.feature.profile.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.CARD_NUMBER
import com.bunbeauty.common.Constants.CARD_NUMBER_LABEL
import com.bunbeauty.common.Constants.PHONE_NUMBER
import com.bunbeauty.common.Constants.PHONE_NUMBER_LABEL
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetPaymentBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent

class PaymentBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_payment) {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetPaymentBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            bottomSheetPaymentNcCardNumber.cardText = CARD_NUMBER
            bottomSheetPaymentNcCardNumber.setOnClickListener {
                copyToBuffer(CARD_NUMBER_LABEL, CARD_NUMBER)
                viewModel.showMessage(
                    requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied),
                    true
                )
            }
            bottomSheetPaymentNcPhoneNumber.cardText = PHONE_NUMBER
            bottomSheetPaymentNcPhoneNumber.setOnClickListener {
                copyToBuffer(PHONE_NUMBER_LABEL, PHONE_NUMBER)
                viewModel.showMessage(
                    requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied),
                    true
                )
            }
        }
    }

    private fun copyToBuffer(label: String, data: String) {
        val clipboard =
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, data)
        clipboard?.setPrimaryClip(clip)
    }
}