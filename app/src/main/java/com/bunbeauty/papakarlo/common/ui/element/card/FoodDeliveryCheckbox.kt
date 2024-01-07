package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.runtime.Composable

@Composable
fun FoodDeliveryCheckbox(
    checked: Boolean,
    colors: CheckboxColors = FoodDeliveryCheckboxDefaults.checkboxColors,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = colors
    )
}
