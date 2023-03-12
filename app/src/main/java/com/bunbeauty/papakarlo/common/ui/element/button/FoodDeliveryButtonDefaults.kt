package com.bunbeauty.papakarlo.common.ui.element.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

object FoodDeliveryButtonDefaults {

    val mainButtonColors: ButtonColors
        @Composable get() = ButtonDefaults.buttonColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.primary,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            contentColor = FoodDeliveryTheme.colors.mainColors.onPrimary
        )

    val secondaryButtonColors: ButtonColors
        @Composable get() = ButtonDefaults.buttonColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.secondary,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            contentColor = FoodDeliveryTheme.colors.mainColors.onSecondary
        )

    val mainOutlineButtonColors: ButtonColors
        @Composable get() = ButtonDefaults.outlinedButtonColors(
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            contentColor = FoodDeliveryTheme.colors.mainColors.primary
        )

    val iconButtonColors: IconButtonColors
        @Composable get() = IconButtonDefaults.iconButtonColors(
            containerColor = FoodDeliveryTheme.colors.mainColors.surface,
            disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
            disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            contentColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        )

}