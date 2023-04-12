package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
    onClick: () -> Unit,
) {
    val color = if (categoryItem.isSelected) {
        FoodDeliveryTheme.colors.mainColors.primary
    } else {
        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
    }
    FoodDeliveryCard(
        modifier = modifier
            .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 2.dp,
            color = color
        )
    ) {
        Text(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                ),
            text = categoryItem.name,
            style = FoodDeliveryTheme.typography.labelLarge.medium,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun CategoryItemSelectedPreview() {
    FoodDeliveryTheme {
        CategoryItem(
            categoryItem = CategoryItem(
                key = "",
                uuid = "",
                name = "Бургеры",
                isSelected = true
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun CategoryItemNotSelectedPreview() {
    FoodDeliveryTheme {
        CategoryItem(
            categoryItem = CategoryItem(
                key = "",
                uuid = "",
                name = "Бургеры",
                isSelected = false
            ),
            onClick = {}
        )
    }
}
