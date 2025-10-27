package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FoodDeliveryCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    colors: CheckboxColors = FoodDeliveryCheckboxDefaults.checkboxColors,
) {
    Checkbox(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors,
    )
}
