package com.bunbeauty.papakarlo.compose.element

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.smallIcon
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.edit_text.EditTextType

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    initTextFieldValue: TextFieldValue = TextFieldValue(""),
    @StringRes labelStringId: Int,
    editTextType: EditTextType,
    maxLines: Int = 1,
    focus: Boolean = false,
    onTextChanged: (TextFieldValue) -> Unit,
) {
    val keyboardOptions = when (editTextType) {
        EditTextType.TEXT -> KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = true,
            keyboardType = KeyboardType.Text
        )
        EditTextType.EMAIL -> KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Email
        )
    }
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = initTextFieldValue,
        onValueChange = { value ->
            onTextChanged(value)
        },
        textStyle = FoodDeliveryTheme.typography.body1,
        colors = FoodDeliveryTheme.colors.textFieldColors(),
        label = {
            Text(text = stringResource(labelStringId))
        },
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        singleLine = maxLines == 1,
        trailingIcon = {
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
    )
    if (focus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
fun EditTextPreview() {
    EditText(
        initTextFieldValue = TextFieldValue("Нужно больше еды \n ..."),
        labelStringId = R.string.hint_create_order_comment,
        editTextType = EditTextType.TEXT
    ) {

    }
}