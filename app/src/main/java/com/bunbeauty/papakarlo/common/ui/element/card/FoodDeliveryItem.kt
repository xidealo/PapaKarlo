package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun FoodDeliveryItem(
    needDivider: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        content()
        if (needDivider) {
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = FoodDeliveryTheme.colors.mainColors.stroke
            )
        }
    }
}
