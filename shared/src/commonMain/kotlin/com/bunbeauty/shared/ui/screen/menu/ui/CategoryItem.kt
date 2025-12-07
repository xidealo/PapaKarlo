package com.bunbeauty.shared.ui.screen.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.shared.presentation.menu.model.CategoryItem

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
    onClick: () -> Unit,
) {
    val color =
        if (categoryItem.isSelected) {
            FoodDeliveryTheme.colors.mainColors.primary
        } else {
            FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
        }
    FoodDeliveryCard(
        modifier = modifier,
        elevated = false,
        onClick = onClick,
        colors = FoodDeliveryCardDefaults.transparentCardColors,
        shape = RoundedCornerShape(16.dp),
        border =
            BorderStroke(
                width = 2.dp,
                color = color,
            ),
    ) {
        Text(
            modifier =
                Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 6.dp,
                    ),
            text = categoryItem.name,
            style = FoodDeliveryTheme.typography.labelLarge.medium,
            color = color,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun CategoryItemSelectedPreview() {
    FoodDeliveryTheme {
        CategoryItem(
            categoryItem =
                CategoryItem(
                    key = "",
                    uuid = "",
                    name = "Бургеры",
                    isSelected = true,
                ),
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun CategoryItemNotSelectedPreview() {
    FoodDeliveryTheme {
        CategoryItem(
            categoryItem =
                CategoryItem(
                    key = "",
                    uuid = "",
                    name = "Бургеры",
                    isSelected = false,
                ),
            onClick = {},
        )
    }
}
