package com.bunbeauty.papakarlo.common.ui.element.text_field

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryTextFieldDefaults {

    val textFieldColors: TextFieldColors
        @Composable get() = TextFieldDefaults.colors(
            // textColor = FoodDeliveryTheme.colors.mainColors.onSurface,
            disabledTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            // containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            cursorColor = FoodDeliveryTheme.colors.mainColors.primary,
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
            // placeholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            disabledPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        )

    val smsCodeTextFieldColors: TextFieldColors
        @Composable get() = TextFieldDefaults.colors(
            // textColor = FoodDeliveryTheme.colors.mainColors.onSurface,
            disabledTextColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            // containerColor = FoodDeliveryTheme.colors.mainColors.surface,
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
            // placeholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
            disabledPlaceholderColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        )

    val textSelectionColors: TextSelectionColors
        @Composable get() = TextSelectionColors(
            handleColor = FoodDeliveryTheme.colors.mainColors.primary,
            backgroundColor = FoodDeliveryTheme.colors.mainColors.primary.copy(alpha = 0.4f)
        )

    val smsCodeTextSelectionColors: TextSelectionColors
        @Composable get() = TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = Color.Transparent
        )
}
