package com.bunbeauty.papakarlo.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

fun Modifier.icon24() = this.size(24.dp)
fun Modifier.icon16() = this.size(16.dp)

fun Modifier.card() = composed {
    fillMaxWidth()
        .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
}
