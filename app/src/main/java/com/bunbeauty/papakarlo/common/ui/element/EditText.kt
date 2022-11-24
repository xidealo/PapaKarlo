package com.bunbeauty.papakarlo.common.ui.element

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.smallIcon
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.edit_text.model.EditTextType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditText(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    editTextType: EditTextType,
    maxLines: Int = 1,
    isLast: Boolean = false,
    focus: Boolean = false,
    @StringRes errorMessageId: Int? = null,
    onTextChanged: (TextFieldValue) -> Unit,
) {
    val imeAction = if (isLast) {
        ImeAction.Done
    } else {
        ImeAction.Next
    }
    val keyboardOptions = when (editTextType) {
        EditTextType.TEXT -> KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        )
        EditTextType.EMAIL -> KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
        )
        EditTextType.PHONE -> KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Phone,
            imeAction = imeAction,
        )
    }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isError = errorMessageId != null

    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = textFieldValue,
        onValueChange = { changedValue ->
            onTextChanged(changedValue)
        },
        textStyle = FoodDeliveryTheme.typography.body1,
        colors = FoodDeliveryTheme.colors.textFieldColors(),
        label = {
            Text(text = stringResource(labelStringId))
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        maxLines = maxLines,
        singleLine = maxLines == 1,
        trailingIcon = {
            if (textFieldValue.text.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .smallIcon()
                        .clickable {
                            onTextChanged(TextFieldValue(""))
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                    contentDescription = stringResource(R.string.description_ic_clear)
                )
            }
        },
        isError = isError
    )
    if (isError) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = errorMessageId?.let {
                stringResource(errorMessageId)
            } ?: "",
            style = FoodDeliveryTheme.typography.body2,
            color = FoodDeliveryTheme.colors.error
        )
    }
    if (focus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
    if (isError) {
        SideEffect {
            focusRequester.requestFocus()
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun EditTextPreview() {
    EditText(
        textFieldValue = TextFieldValue("Нужно больше еды \n ..."),
        labelStringId = R.string.hint_create_order_comment,
        editTextType = EditTextType.TEXT
    ) {
    }
}
