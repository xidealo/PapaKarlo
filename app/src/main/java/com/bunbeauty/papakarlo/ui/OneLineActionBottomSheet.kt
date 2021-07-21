package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.text.InputType
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bunbeauty.common.Constants.DIGITS
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.papakarlo.databinding.BottomSheetOneLineActionBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.presentation.OneLineActionViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class OneLineActionBottomSheet : BaseBottomSheetDialog<BottomSheetOneLineActionBinding>() {

    override val viewModel: OneLineActionViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private val args: OneLineActionBottomSheetArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val oneLineActionModel = args.oneLineActionModel
        viewDataBinding.run {
            bottomSheetOneLineActionTvTitle.text = oneLineActionModel.title
            if (oneLineActionModel.infoText == null) {
                bottomSheetOneLineActionTvInfo.gone()
            } else {
                bottomSheetOneLineActionTvInfo.text = oneLineActionModel.infoText
            }
            bottomSheetOneLineActionTilData.hint = oneLineActionModel.hint
            bottomSheetOneLineActionEtData.setText(oneLineActionModel.inputText)
            bottomSheetOneLineActionBtnSave.text = oneLineActionModel.buttonText
            when (oneLineActionModel.type) {
                OneLineActionType.EMAIL -> {
                    bottomSheetOneLineActionEtData.inputType = TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
                OneLineActionType.BONUSES -> {
                    bottomSheetOneLineActionEtData.keyListener =
                        DigitsKeyListener.getInstance(DIGITS)
                }
                OneLineActionType.COMMENT -> {
                    bottomSheetOneLineActionEtData.inputType = TYPE_TEXT_FLAG_MULTI_LINE
                }
            }
            bottomSheetOneLineActionBtnSave.setOnClickListener {
                val result = bottomSheetOneLineActionEtData.text.toString()
                setFragmentResult(
                    oneLineActionModel.requestKey,
                    bundleOf(oneLineActionModel.resultKey to result)
                )
                viewModel.goBack()
            }
        }
    }
}