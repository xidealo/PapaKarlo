package com.bunbeauty.papakarlo.feature.edit_text

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.compose.element.EditText
import com.bunbeauty.papakarlo.compose.element.MainButton
import com.bunbeauty.papakarlo.compose.element.Title
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetEditTextBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTextBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_edit_text) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetEditTextBinding::bind)

    private val editTextSettings: EditTextSettings by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetEditTextCvMain.compose {
            EditTextScreen(editTextSettings)
        }
    }

    @Composable
    private fun EditTextScreen(editTextSettings: EditTextSettings) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Title(textStringId = editTextSettings.titleStringId)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                if (editTextSettings.infoText != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
                        text = editTextSettings.infoText
                    )
                }
                val inputText = editTextSettings.inputText ?: ""
                var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(
                        TextFieldValue(
                            text = inputText,
                            selection = TextRange(inputText.length)
                        )
                    )
                }
                EditText(
                    modifier = Modifier.fillMaxWidth(),
                    textFieldValue = textFieldValue,
                    labelStringId = editTextSettings.labelStringId,
                    editTextType = editTextSettings.type,
                    isLast = true,
                    focus = true
                ) { changedValue ->
                    textFieldValue = changedValue
                }
                MainButton(
                    modifier = Modifier.padding(vertical = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_settings_save,
                    hasShadow = false
                ) {
                    setFragmentResult(
                        editTextSettings.requestKey,
                        bundleOf(editTextSettings.resultKey to textFieldValue.text)
                    )
                    viewModel.goBack()
                }
            }
        }
    }

    @Preview
    @Composable
    private fun EditTextScreenPreview() {
        EditTextScreen(
            EditTextSettings(
                titleStringId = R.string.title_settings_edit_email,
                infoText = "Отредактируйте его",
                labelStringId = R.string.hint_settings_email,
                type = EditTextType.EMAIL,
                inputText = "aaa@aa.com",
                buttonStringId = R.string.action_settings_save,
                requestKey = "",
                resultKey = ""
            )
        )
    }

    @Preview
    @Composable
    private fun EditTextScreenWithoutInfoPreview() {
        EditTextScreen(
            EditTextSettings(
                titleStringId = R.string.title_settings_edit_email,
                infoText = null,
                labelStringId = R.string.hint_settings_email,
                type = EditTextType.EMAIL,
                inputText = "aaa@aa.com",
                buttonStringId = R.string.action_settings_save,
                requestKey = "",
                resultKey = ""
            )
        )
    }
}