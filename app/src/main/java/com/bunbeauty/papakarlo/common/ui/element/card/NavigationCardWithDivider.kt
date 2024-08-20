package com.bunbeauty.papakarlo.common.ui.element.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryHorizontalDivider
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCardDefaults.zeroCardShape
import com.bunbeauty.papakarlo.common.ui.icon16
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@Composable
fun NavigationCardWithDivider(
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    label: String,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        clickable = clickable,
        elevated = false,
        onClick = onClick,
        shape = zeroCardShape
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OverflowingText(
                    text = label,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.icon16(),
                    painter = painterResource(R.drawable.ic_right_arrow),
                    tint = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                    contentDescription = null
                )
            }

            FoodDeliveryHorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

    }
}

@Preview
@Composable
private fun NavigationCardWithDividerPreview() {
    FoodDeliveryTheme {
        NavigationCard(
            label = "Название раздела",
            onClick = {}
        )
    }
}
