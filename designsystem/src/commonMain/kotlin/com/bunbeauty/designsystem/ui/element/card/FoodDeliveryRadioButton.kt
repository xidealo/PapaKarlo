package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FoodDeliveryRadioButton(
    selected: Boolean,
    colors: RadioButtonColors = FoodDeliveryRadioButtonDefaults.radioButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RadioButton(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        colors = colors,
    )
}
