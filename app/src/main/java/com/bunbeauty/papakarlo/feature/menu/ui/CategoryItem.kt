package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import com.bunbeauty.papakarlo.common.ui.theme.buttonRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.chipCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
    onClick: () -> Unit,
) {
    val color = if (categoryItem.isSelected) {
        FoodDeliveryTheme.colors.primary
    } else {
        FoodDeliveryTheme.colors.onSurfaceVariant
    }
    FoodDeliveryCard(
        modifier = modifier
            .heightIn(min = FoodDeliveryTheme.dimensions.smallButtonSize),
        onClick = onClick,
        shape = chipCornerShape,
        border = BorderStroke(
            width = 2.dp, color = color
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
