package com.bunbeauty.papakarlo.ui.fragment

import android.os.Bundle
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.enums.OneLineActionType
import com.bunbeauty.domain.model.OneLineActionModel
import com.bunbeauty.papakarlo.databinding.BottomSheetOneLineActionBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet

class OneLineActionBottomSheet : BaseBottomSheet<BottomSheetOneLineActionBinding>() {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    private val oneLineActionModel: OneLineActionModel by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                OneLineActionType.TEXT -> {
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