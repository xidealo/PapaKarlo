package com.bunbeauty.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.textfield.FoodDeliveryTextFieldDefaults
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SmsEditText(
    modifier: Modifier = Modifier,
    smsCodeLength: Int = 6,
    onFilled: (smsCode: String) -> Unit,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var isFilled by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val code = textFieldValue.text
    val activeIndex = textFieldValue.selection.start.coerceIn(0, smsCodeLength - 1)

    CompositionLocalProvider(
        LocalTextSelectionColors provides FoodDeliveryTextFieldDefaults.smsCodeTextSelectionColors,
    ) {
        Box(modifier = modifier) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val filtered =
                        newValue.text
                            .filter { char -> char in '0'..'9' }
                            .take(smsCodeLength)
                    val selection =
                        TextRange(
                            start = newValue.selection.start.coerceIn(0, filtered.length),
                            end = newValue.selection.end.coerceIn(0, filtered.length),
                        )
                    val becameFilled = filtered.length == smsCodeLength && !isFilled
                    textFieldValue = TextFieldValue(text = filtered, selection = selection)
                    isFilled = filtered.length == smsCodeLength
                    if (becameFilled) {
                        onFilled(filtered)
                    }
                },
                modifier =
                    Modifier
                        .matchParentSize()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                textStyle =
                    TextStyle(
                        color = Color.Transparent,
                        textAlign = TextAlign.Center,
                    ),
                cursorBrush = SolidColor(Color.Transparent),
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.None,
                    ),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = spacedBy(8.dp),
            ) {
                repeat(smsCodeLength) { index ->
                    val digit = code.getOrNull(index)?.toString().orEmpty()
                    SmsDigitBox(
                        modifier = Modifier.weight(1f),
                        digit = digit,
                        isActive = isFocused && index == activeIndex,
                        onClick = {
                            focusRequester.requestFocus()
                            val cursor = index.coerceAtMost(code.length)
                            textFieldValue =
                                textFieldValue.copy(
                                    selection = TextRange(cursor),
                                )
                        },
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun SmsDigitBox(
    digit: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Visual analogue of FoodDeliveryTextFieldDefaults.smsCodeTextFieldColors
    val indicatorColor =
        if (isActive) {
            FoodDeliveryTheme.colors.mainColors.primary
        } else {
            FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        }
    val indicatorThickness =
        if (isActive) {
            2.dp
        } else {
            1.dp
        }

    Box(
        modifier =
            modifier
                .height(FoodDeliveryTheme.dimensions.smsDigitHeight)
                .background(FoodDeliveryTheme.colors.mainColors.surface)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                )
                .drawBehind {
                    val strokeWidth = indicatorThickness.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = indicatorColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth,
                    )
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = digit,
            style =
                FoodDeliveryTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                ),
            color = FoodDeliveryTheme.colors.mainColors.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SmsEditTextPreview() {
    FoodDeliveryTheme {
        SmsEditText(onFilled = {})
    }
}
