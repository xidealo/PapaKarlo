package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.chipCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier
            .heightIn(min = FoodDeliveryTheme.dimensions.smallButtonSize),
        onClick = onClick,
        colors = if (categoryItem.isSelected) {
            CardDefaults.cardColors(containerColor = FoodDeliveryTheme.colors.primary)
        } else {
            CardDefaults.cardColors(containerColor = FoodDeliveryTheme.colors.surface)
        },
        shape = chipCornerShape
    ) {
        val color = if (categoryItem.isSelected) {
            FoodDeliveryTheme.colors.onPrimary
        } else {
            FoodDeliveryTheme.colors.onSurface
        }
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = categoryItem.name,
                style = FoodDeliveryTheme.typography.labelLarge.medium,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun CategoryItemSelectedPreview() {
    CategoryItem(
        categoryItem = CategoryItem(
            key = "",
            uuid = "",
            name = "Бургеры",
            isSelected = true
        )
    ) {}
}

@Preview
@Composable
fun CategoryItemNotSelectedPreview() {
    CategoryItem(
        categoryItem = CategoryItem(
            key = "",
            uuid = "",
            name = "Бургеры",
            isSelected = false
        )
    ) {}
}
