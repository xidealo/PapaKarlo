package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.common.ui.theme.smallRoundedCornerShape
import com.bunbeauty.papakarlo.feature.menu.model.CategoryItem

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItem: CategoryItem,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .defaultMinSize(minHeight = FoodDeliveryTheme.dimensions.smallButtonSize)
            .shadow(
                elevation = FoodDeliveryTheme.dimensions.getEvaluation(true),
                shape = mediumRoundedCornerShape
            )
            .clip(smallRoundedCornerShape)
            .clickable(
                enabled = !categoryItem.isSelected,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = if (categoryItem.isSelected) {
            FoodDeliveryTheme.colors.primary
        } else {
            FoodDeliveryTheme.colors.surface
        }
    ) {
        val style = if (categoryItem.isSelected) {
            FoodDeliveryTheme.typography.smallButton
        } else {
            FoodDeliveryTheme.typography.body2
        }
        val color = if (categoryItem.isSelected) {
            FoodDeliveryTheme.colors.onPrimary
        } else {
            FoodDeliveryTheme.colors.onSurface
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    horizontal = FoodDeliveryTheme.dimensions.mediumSpace,
                    vertical = FoodDeliveryTheme.dimensions.smallSpace
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = categoryItem.name,
                style = style,
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