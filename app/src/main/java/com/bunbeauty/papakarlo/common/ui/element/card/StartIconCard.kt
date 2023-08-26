package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.icon24
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun StartIconCard(
    label: String,
    @DrawableRes iconId: Int,
    modifier: Modifier = Modifier,
    elevated: Boolean = true,
    iconTint: Color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
    onClick: (() -> Unit) = {},
    clickable: Boolean = true
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        elevated = elevated,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.icon24(),
                painter = painterResource(iconId),
                tint = iconTint,
                contentDescription = label
            )
            OverflowingText(
                modifier = Modifier.padding(start = 16.dp),
                text = label,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun IconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Ооооооооооооооочень длинная информация",
            iconId = R.drawable.ic_info
        )
    }
}

@Preview
@Composable
private fun OriginalColorIconCardPreview() {
    FoodDeliveryTheme {
        StartIconCard(
            label = "Информация",
            iconId = R.drawable.ic_bb,
            iconTint = FoodDeliveryTheme.colors.bunBeautyBrandColor
        )
    }
}
