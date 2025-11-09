package com.bunbeauty.shared.ui.common.ui.element.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.bunbeauty.shared.ui.theme.FoodDeliveryTheme

object FoodDeliveryButtonDefaults {
    private val buttonElevation: ButtonElevation
        @Composable get() = ButtonDefaults.buttonElevation(2.dp)

    private val zeroButtonElevation: ButtonElevation
        @Composable get() = ButtonDefaults.buttonElevation(0.dp)

    @Composable
    fun mainButtonColors(enabled: Boolean = true): ButtonColors {
        val containerColor by animateColorAsState(
            targetValue =
                if (enabled) {
                    FoodDeliveryTheme.colors.mainColors.primary
                } else {
                    FoodDeliveryTheme.colors.mainColors.disabled
                },
            animationSpec = tween(),
            label = "containerColor",
        )
        val contentColor by animateColorAsState(
            targetValue =
                if (enabled) {
                    FoodDeliveryTheme.colors.mainColors.onPrimary
                } else {
                    FoodDeliveryTheme.colors.mainColors.onDisabled
                },
            animationSpec = tween(),
            label = "contentColor",
        )

        return ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor,
        )
    }

    val secondaryButtonColors: ButtonColors
        @Composable get() =
            ButtonDefaults.buttonColors(
                containerColor = FoodDeliveryTheme.colors.mainColors.secondary,
                contentColor = FoodDeliveryTheme.colors.mainColors.onSecondary,
                disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
                disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            )

    val mainOutlineButtonColors: ButtonColors
        @Composable get() =
            ButtonDefaults.outlinedButtonColors(
                disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
                disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
                contentColor = FoodDeliveryTheme.colors.mainColors.primary,
            )

    val iconButtonColors: IconButtonColors
        @Composable get() =
            IconButtonDefaults.iconButtonColors(
                containerColor = FoodDeliveryTheme.colors.mainColors.surface,
                contentColor = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                disabledContainerColor = FoodDeliveryTheme.colors.mainColors.disabled,
                disabledContentColor = FoodDeliveryTheme.colors.mainColors.onDisabled,
            )

    val buttonShape: RoundedCornerShape
        @Composable get() = RoundedCornerShape(FoodDeliveryTheme.dimensions.buttonRadius)

    @Composable
    fun getButtonElevation(elevated: Boolean): ButtonElevation =
        if (elevated) {
            buttonElevation
        } else {
            zeroButtonElevation
        }
}
