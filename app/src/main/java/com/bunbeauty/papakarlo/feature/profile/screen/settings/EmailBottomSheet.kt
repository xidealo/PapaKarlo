package com.bunbeauty.papakarlo.feature.profile.screen.settings

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
import androidx.compose.ui.text.input.KeyboardType
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

class EmailBottomSheet : ComposeBottomSheet<String>() {

    private var email by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            EmailScreen(email = email) { updatedComment ->
                callback?.onResult(updatedComment)
            }
        }
    }

    companion object {
        private const val TAG = "EmailBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            email: String?,
        ) = suspendCoroutine { continuation ->
            EmailBottomSheet().apply {
                this.email = email
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
private fun EmailScreen(
    email: String?,
    onSaveClicked: (String) -> Unit,
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.common_email) {
        val text = email ?: ""
        var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            )
        }

        val focusRequester = remember { FocusRequester() }
        FoodDeliveryTextField(
            modifier = Modifier.fillMaxWidth(),
            focusRequester = focusRequester,
            value = textFieldValue,
            labelStringId = R.string.common_email,
            keyboardType = KeyboardType.Email,
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

@Preview(showSystemUi = true)
@Composable
private fun EmailScreenPreview() {
    FoodDeliveryTheme {
        EmailScreen(email = "example@email.com") {}
    }
}
