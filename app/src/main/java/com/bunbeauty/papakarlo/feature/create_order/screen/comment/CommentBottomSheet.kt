package com.bunbeauty.papakarlo.feature.create_order.screen.comment

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.screen.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CommentBottomSheet : ComposeBottomSheet<String>() {

    private var comment by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            CommentScreen(comment = comment) { updatedComment ->
                callback?.onResult(updatedComment)
            }
        }
    }

    companion object {
        private const val TAG = "CommentBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            comment: String?,
        ) = suspendCoroutine { continuation ->
            CommentBottomSheet().apply {
                this.comment = comment
                callback = object : Callback<String> {
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
    onSaveClicked: (String) -> Unit,
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.comment) {
        val focusRequester = remember { FocusRequester() }
        val text = comment ?: ""
        var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            )
        }
        FoodDeliveryTextField(
            modifier = Modifier.fillMaxWidth(),
            focusRequester = focusRequester,
            value = textFieldValue,
            labelStringId = R.string.comment,
            imeAction = ImeAction.Done,
            onValueChange = { value ->
                textFieldValue = value
            }
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        MainButton(
            modifier = Modifier.padding(top = 16.dp),
            textStringId = R.string.action_settings_save,
            elevated = false,
            onClick = {
                onSaveClicked(textFieldValue.text)
            }
        )
    }
}

@Preview
@Composable
private fun CommentScreenPreview() {
    FoodDeliveryTheme {
        CommentScreen(
            comment = null,
            onSaveClicked = {},
        )
    }
}
