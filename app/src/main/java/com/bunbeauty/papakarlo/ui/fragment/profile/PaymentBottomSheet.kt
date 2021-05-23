package com.bunbeauty.papakarlo.ui.fragment.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.CARD_NUMBER
import com.bunbeauty.common.Constants.CARD_NUMBER_LABEL
import com.bunbeauty.common.Constants.PHONE_NUMBER
import com.bunbeauty.common.Constants.PHONE_NUMBER_LABEL
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetPaymentBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet

class PaymentBottomSheet : BaseBottomSheet<BottomSheetPaymentBinding>() {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            bottomSheetPaymentNcCardNumber.cardText = CARD_NUMBER
            bottomSheetPaymentNcCardNumber.setOnClickListener {
                copyToBuffer(CARD_NUMBER_LABEL, CARD_NUMBER)
                viewModel.showMessage(requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied))
            }
            bottomSheetPaymentNcPhoneNumber.cardText = PHONE_NUMBER
            bottomSheetPaymentNcPhoneNumber.setOnClickListener {
                copyToBuffer(PHONE_NUMBER_LABEL, PHONE_NUMBER)
                viewModel.showMessage(requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied))
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