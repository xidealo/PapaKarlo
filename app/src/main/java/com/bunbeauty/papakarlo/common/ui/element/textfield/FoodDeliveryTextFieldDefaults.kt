package com.bunbeauty.papakarlo.common.ui.element.textfield

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryTextFieldDefaults {
    val textFieldColors: TextFieldColors
        @Composable get() =
            TextFieldDefaults.colors(
                focusedTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                unfocusedTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                disabledTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                errorTextColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                unfocusedContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                errorContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                cursorColor = FoodDeliveryTheme.colors.mainColors.primary,
                errorCursorColor = FoodDeliveryTheme.colors.mainColors.error,
                selectionColors =
                    TextSelectionColors(
                        handleColor =
                            FoodDeliveryTheme.colors.mainColors.primary
                                .copy(0.2f),
                        backgroundColor =
                            FoodDeliveryTheme.colors.mainColors.primary
                                .copy(0.2f),
                    ),
                focusedIndicatorColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedIndicatorColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledIndicatorColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorIndicatorColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedLeadingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                unfocusedLeadingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledLeadingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorLeadingIconColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedTrailingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                unfocusedTrailingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledTrailingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorTrailingIconColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedLabelColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedLabelColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledLabelColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorLabelColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedPlaceholderColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorPlaceholderColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedSupportingTextColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedSupportingTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledSupportingTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorSupportingTextColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedPrefixColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedPrefixColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledPrefixColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorPrefixColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedSuffixColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedSuffixColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledSuffixColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorSuffixColor = FoodDeliveryTheme.colors.mainColors.error,
            )

    val smsCodeTextFieldColors: TextFieldColors
        @Composable get() =
            TextFieldDefaults.colors(
                focusedTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                unfocusedTextColor = FoodDeliveryTheme.colors.mainColors.onSurface,
                errorTextColor = FoodDeliveryTheme.colors.mainColors.error,
                disabledTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                focusedContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                unfocusedContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                disabledContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                errorContainerColor = FoodDeliveryTheme.colors.mainColors.surface,
                cursorColor = Color.Transparent,
                errorCursorColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedIndicatorColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedIndicatorColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledIndicatorColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorIndicatorColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedLeadingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledLeadingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorLeadingIconColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedTrailingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledTrailingIconColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorTrailingIconColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedLabelColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedLabelColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledLabelColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorLabelColor = FoodDeliveryTheme.colors.mainColors.error,
                focusedPlaceholderColor = FoodDeliveryTheme.colors.mainColors.primary,
                unfocusedPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                errorPlaceholderColor = FoodDeliveryTheme.colors.mainColors.error,
            )

    val textSelectionColors: TextSelectionColors
        @Composable get() =
            TextSelectionColors(
                handleColor = FoodDeliveryTheme.colors.mainColors.primary,
                backgroundColor =
                    FoodDeliveryTheme.colors.mainColors.primary
                        .copy(alpha = 0.4f),
            )

    val smsCodeTextSelectionColors: TextSelectionColors
        @Composable get() =
            TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = Color.Transparent,
            )

    fun keyboardOptions(
        autoCorrect: Boolean = false,
        keyboardType: KeyboardType = KeyboardType.Text,
        imeAction: ImeAction = ImeAction.Next,
    ): KeyboardOptions =
        KeyboardOptions(
            autoCorrect = autoCorrect,
            keyboardType = keyboardType,
            imeAction = imeAction,
        )

    fun keyboardActions(onDone: (KeyboardActionScope.() -> Unit)? = null): KeyboardActions =
        KeyboardActions(
            onDone = onDone,
        )
}
