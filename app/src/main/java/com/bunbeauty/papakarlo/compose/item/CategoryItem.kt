package com.bunbeauty.papakarlo.compose.item

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
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.compose.theme.smallRoundedCornerShape
import com.bunbeauty.papakarlo.feature.menu.view_state.CategoryItemModel

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryItemModel: CategoryItemModel,
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
                enabled = !categoryItemModel.isSelected,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = if (categoryItemModel.isSelected) {
            FoodDeliveryTheme.colors.primary
        } else {
            FoodDeliveryTheme.colors.surface
        }
    ) {
        val style = if (categoryItemModel.isSelected) {
            FoodDeliveryTheme.typography.h3
        } else {
            FoodDeliveryTheme.typography.body2
        }
        val color = if (categoryItemModel.isSelected) {
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
                text = categoryItemModel.name,
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
        categoryItemModel = CategoryItemModel(
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
        categoryItemModel = CategoryItemModel(
            key = "",
            uuid = "",
            name = "Бургеры",
            isSelected = false
        )
    ) {}
}