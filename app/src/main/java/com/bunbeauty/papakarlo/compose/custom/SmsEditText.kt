package com.bunbeauty.papakarlo.compose.custom

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme

@Composable
fun SmsEditText(
    modifier: Modifier = Modifier,
    smsCodeLength: Int = 6,
    onFilled: (smsCode: String) -> Unit
) {
    val enteredNumbers: SnapshotStateList<String> = remember {
        mutableStateListOf(
            *((0 until smsCodeLength).map { "" }.toTypedArray())
        )
    }
    val focusRequesters: List<FocusRequester> = remember {
        (0 until smsCodeLength).map { FocusRequester() }
    }
    var isFilled: Boolean by remember {
        mutableStateOf(false)
    }
    Row(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides FoodDeliveryTheme.colors.smsTextSelectionColors
        ) {
            repeat(smsCodeLength) { i ->
                SmsDigitCell(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)),
                    value = enteredNumbers[i],
                    focusRequester = focusRequesters[i],
                    onValueChanged = { changedValue ->
                        enteredNumbers[i] = changedValue
                        if (changedValue != "" && i < focusRequesters.lastIndex) {
                            focusRequesters[i + 1].requestFocus()
                        }
                        if (enteredNumbers.none { enteredNumber -> enteredNumber.isBlank() }
                            && !isFilled
                        ) {
                            isFilled = true
                            onFilled(enteredNumbers.joinToString(separator = ""))
                        }
                    },
                    onCurrentRemoved = {
                        enteredNumbers[i] = ""
                    },
                    onPreviousRemoved = {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SmsDigitCell(
    modifier: Modifier = Modifier,
    value: String,
    focusRequester: FocusRequester,
    onValueChanged: (String) -> Unit,
    onCurrentRemoved: () -> Unit,
    onPreviousRemoved: () -> Unit
) {
    TextField(
        modifier = modifier
            .size(
                width = FoodDeliveryTheme.dimensions.smsEditTextWidth,
                height = FoodDeliveryTheme.dimensions.smsEditTextHeight
            )
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp) {
                    if (event.key == Key.Backspace) {
                        if (value == "") {
                            onPreviousRemoved()
                        } else {
                            onCurrentRemoved()
                        }
                    }
                }
                true
            }
            .focusOrder(focusRequester)
            .focusRequester(focusRequester),
        colors = FoodDeliveryTheme.colors.smsTextFieldColors(),
        textStyle = FoodDeliveryTheme.typography.body1.copy(textAlign = TextAlign.Center),
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

@Preview
@Composable
fun SmsEditTextPreview() {
    SmsEditText {}
}