package com.bunbeauty.designsystem.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.FoodDeliveryHorizontalDivider

@Composable
fun FoodDeliveryItem(
    modifier: Modifier = Modifier,
    needDivider: Boolean,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        content()
        if (needDivider) {
            FoodDeliveryHorizontalDivider(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp),
            )
        }
    }
}
