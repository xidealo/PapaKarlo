package com.bunbeauty.papakarlo.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetPaymentBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class PaymentBottomSheet : BaseBottomSheetDialog<BottomSheetPaymentBinding>() {

    override val viewModel: MainViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewDataBinding) {
            bottomSheetPaymentBtnCardNumber.setOnClickListener {
                copyToBuffer(
                    "card number",
                    requireContext().getString(R.string.pay_data_card_number)
                )
                showMessage(requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied))
            }
            bottomSheetPaymentBtnPhoneNumber.setOnClickListener {
                copyToBuffer(
                    "phone number",
                    requireContext().getString(R.string.pay_data_phone_number)
                )
                showMessage(requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied))
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