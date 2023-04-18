package com.bunbeauty.papakarlo.feature.auth.ui

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.bunbeauty.papakarlo.common.ui.element.text_field.FoodDeliveryTextFieldDefaults
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun SmsEditText(
    modifier: Modifier = Modifier,
    smsCodeLength: Int = 6,
    onFilled: (smsCode: String) -> Unit
) {
    val enteredNumbers: SnapshotStateList<String> = remember {
        (0 until smsCodeLength).map { "" }.toMutableStateList()
    }
    val focusRequesters: List<FocusRequester> = remember {
        (0 until smsCodeLength).map { FocusRequester() }
    }
    var isFilled: Boolean by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = spacedBy(8.dp),
    ) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides FoodDeliveryTextFieldDefaults.smsCodeTextSelectionColors
        ) {
            repeat(smsCodeLength) { i ->
                SmsDigitCell(
                    modifier = Modifier.weight(1f),
                    value = enteredNumbers[i],
                    focusRequester = focusRequesters[i],
                    onValueChanged = { changedValue ->
                        enteredNumbers[i] = changedValue
                        if (changedValue != "" && i < focusRequesters.lastIndex) {
                            focusRequesters[i + 1].requestFocus()
                        }
                        if (enteredNumbers.none { enteredNumber -> enteredNumber.isBlank() } &&
                            !isFilled
                        ) {
                            isFilled = true
                            onFilled(enteredNumbers.joinToString(separator = ""))
                        }
                    },
                    onFilledRemoved = {
                        enteredNumbers[i] = ""
                    },
                    onEmptyRemoved = {
                        if (i > 0) {
                            enteredNumbers[i - 1] = ""
                            focusRequesters[i - 1].requestFocus()
                        }
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SmsDigitCell(
    modifier: Modifier = Modifier,
    value: String,
    focusRequester: FocusRequester,
    onValueChanged: (String) -> Unit,
    onFilledRemoved: () -> Unit,
    onEmptyRemoved: () -> Unit
) {
    TextField(
        modifier = modifier
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp) {
                    if (event.key == Key.Backspace) {
                        if (value == "") {
                            onEmptyRemoved()
                        } else {
                            onFilledRemoved()
                        }
                    }
                }
                true
            }
            .focusRequester(focusRequester),
        colors = FoodDeliveryTextFieldDefaults.smsCodeTextFieldColors,
        textStyle = FoodDeliveryTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
        singleLine = true,
        value = TextFieldValue(value),
        onValueChange = { textFieldValue: TextFieldValue ->
            if (textFieldValue.text.isDigitsOnly()) {
                if (textFieldValue.selection.start == 1) {
                    onValueChanged(textFieldValue.text.first().toString())
                } else {
                    onValueChanged(textFieldValue.text.last().toString())
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.None
        ),
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SmsEditTextPreview() {
    FoodDeliveryTheme {
        SmsEditText(onFilled = {})
    }
}
