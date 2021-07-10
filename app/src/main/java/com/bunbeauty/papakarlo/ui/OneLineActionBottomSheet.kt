package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.text.method.KeyListener
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetOneLineActionBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.OneLineActionViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class OneLineActionBottomSheet : BaseBottomSheetDialog<BottomSheetOneLineActionBinding>() {

    override var layoutId = R.layout.bottom_sheet_one_line_action
    override val viewModel: OneLineActionViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewDataBinding) {
            bottomSheetOneLineActionTvTitle.text =
                OneLineActionBottomSheetArgs.fromBundle(requireArguments()).oneLineActionModel.title
            bottomSheetOneLineActionBtnSave.text =
                OneLineActionBottomSheetArgs.fromBundle(requireArguments()).oneLineActionModel.buttonText

            bottomSheetOneLineActionEtData.setText(
                OneLineActionBottomSheetArgs.fromBundle(
                    requireArguments()
                ).oneLineActionModel.data
            )

            when (OneLineActionBottomSheetArgs.fromBundle(requireArguments()).oneLineActionModel.type) {
                OneLineActionType.EMAIL -> {

                }
                OneLineActionType.BONUSES -> {
                    bottomSheetOneLineActionEtData.keyListener = DigitsKeyListener.getInstance("0123456789")
                }
                OneLineActionType.COMMENT -> { }
            }

        }
    }

}