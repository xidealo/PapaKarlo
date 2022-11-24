package com.bunbeauty.papakarlo.feature.create_order.screen.comment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.element.EditText
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetBinding
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CommentBottomSheet : BottomSheetDialogFragment() {

    private var comment by nullableArgument<String>()
    private var callback: Callback? = null

    private val binding by viewBinding(BottomSheetBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, true)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            CommentScreen(comment = comment) { updatedComment ->
                callback?.onResult(updatedComment)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        callback?.onResult(null)
    }

    private interface Callback {
        fun onResult(result: String?)
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            comment: String?
        ) = suspendCoroutine { continuation ->
            CommentBottomSheet().apply {
                this.comment = comment
                callback = object : Callback {
                    override fun onResult(result: String?) {
                        continuation.resume(result)
                        dismiss()
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun CommentScreen(
    comment: String?,
    onSaveClicked: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.comment),
            style = FoodDeliveryTheme.typography.h2,
            color = FoodDeliveryTheme.colors.onSurface
        )
        val text = comment ?: ""
        var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            )
        }
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            textFieldValue = textFieldValue,
            labelStringId = R.string.comment,
            editTextType = EditTextType.TEXT,
            isLast = true,
            focus = true,
        ) { newTextFieldValue ->
            textFieldValue = newTextFieldValue
        }
        MainButton(
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
            textStringId = R.string.action_settings_save,
            hasShadow = false
        ) {
            onSaveClicked(textFieldValue.text)
        }
    }
}
