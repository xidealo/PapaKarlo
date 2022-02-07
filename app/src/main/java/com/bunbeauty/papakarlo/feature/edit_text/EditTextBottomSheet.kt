package com.bunbeauty.papakarlo.feature.edit_text

import android.os.Bundle
import android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetEditTextBinding
import com.bunbeauty.papakarlo.extensions.gone
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTextBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_edit_text) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetEditTextBinding::bind)

    private val editTextSettings: EditTextSettings by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            bottomSheetOneLineActionTvTitle.text = editTextSettings.title
            if (editTextSettings.infoText == null) {
                bottomSheetOneLineActionTvInfo.gone()
            } else {
                bottomSheetOneLineActionTvInfo.text = editTextSettings.infoText
            }
            bottomSheetOneLineActionTilData.hint = editTextSettings.hint
            bottomSheetOneLineActionEtData.setText(editTextSettings.inputText)
            bottomSheetOneLineActionBtnSave.text = editTextSettings.buttonText
            when (editTextSettings.type) {
                EditTextType.EMAIL -> {
                    bottomSheetOneLineActionEtData.inputType = TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
                EditTextType.TEXT -> {
                    bottomSheetOneLineActionEtData.inputType = TYPE_TEXT_FLAG_MULTI_LINE
                }
            }
            bottomSheetOneLineActionEtData.requestFocus()
            bottomSheetOneLineActionBtnSave.setOnClickListener {
                val result = bottomSheetOneLineActionEtData.text.toString()
                setFragmentResult(
                    editTextSettings.requestKey,
                    bundleOf(editTextSettings.resultKey to result)
                )
                viewModel.goBack()
            }
        }
    }
}