package com.bunbeauty.papakarlo.feature.profile.screen.settings

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextField
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EmailBottomSheet : ComposeBottomSheet<String>() {

    private var email by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            CommentScreen(email = email) { updatedComment ->
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
private fun CommentScreen(
    email: String?,
    onSaveClicked: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Title(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.common_email),
        )

        val focusRequester = remember { FocusRequester() }
        val text = email ?: ""
        var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            )
        }
        FoodDeliveryTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
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
            modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
            textStringId = R.string.action_settings_save,
            hasShadow = false
        ) {
            onSaveClicked(textFieldValue.text)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CommentScreenPreview() {
    FoodDeliveryTheme {
        CommentScreen(email = "example@email.com") {}
    }
}
