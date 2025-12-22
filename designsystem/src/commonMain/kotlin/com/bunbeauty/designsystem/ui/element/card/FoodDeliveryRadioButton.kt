package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable

@Composable
fun FoodDeliveryRadioButton(
    selected: Boolean,
    colors: RadioButtonColors = FoodDeliveryRadioButtonDefaults.radioButtonColors,
    onClick: () -> Unit,
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = colors,
    )
}
