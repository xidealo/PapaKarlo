package com.bunbeauty.papakarlo.feature.edit_text

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTextBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_edit_text) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetEditTextBinding::bind)

    private val editTextSettings: EditTextSettings by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetEditTextCvMain.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                EditTextScreen(editTextSettings)
            }
        }
    }

    @Composable
    private fun EditTextScreen(editTextSettings: EditTextSettings) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Title(textStringId = editTextSettings.titleStringId)
            if (editTextSettings.infoText != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    text = editTextSettings.infoText
                )
            }
            var text by remember { mutableStateOf(editTextSettings.inputText ?: "") }
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                startText = text,
                labelStringId = editTextSettings.labelStringId,
                editTextType = editTextSettings.type,
                focus = true
            ) {
                text = it
            }
            MainButton(
                modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.action_settings_save
            ) {
                setFragmentResult(
                    editTextSettings.requestKey,
                    bundleOf(editTextSettings.resultKey to text)
                )
                viewModel.goBack()
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