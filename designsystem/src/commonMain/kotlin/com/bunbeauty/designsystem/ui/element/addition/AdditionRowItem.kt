package com.bunbeauty.designsystem.ui.element.addition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCardDefaults
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCheckbox
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryRadioButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.description_product_addition

@Composable
fun AdditionRowItem(
    uuid: String,
    groupId: String,
    photoLink: String,
    name: String,
    price: String?,
    isSelected: Boolean,
    isMultiple: Boolean,
    onCardClick: (String, String) -> Unit,
) {
    FoodDeliveryCard(
        onClick = {
            onCardClick(uuid, groupId)
        },
        elevated = false,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FoodDeliveryAsyncImage(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(FoodDeliveryCardDefaults.cardShape),
                photoLink = photoLink,
                contentDescription = stringResource(resource = Res.string.description_product_addition),
                contentScale = ContentScale.FillWidth,
                error = null,
            )

            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                text = name,
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )

            price?.let { price ->
                Text(
                    modifier =
                        Modifier
                            .padding(end = 8.dp),
                    text = price,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }

            if (isMultiple) {
                FoodDeliveryCheckbox(
                    checked = isSelected,
                    onCheckedChange = {
                        onCardClick(uuid, groupId)
                    },
                )
            } else {
                FoodDeliveryRadioButton(
                    selected = isSelected,
                    onClick = {
                        onCardClick(uuid, groupId)
                    },
                )
            }
        }
    }
}

private const val PREVIEW_PHOTO_LINK = ""

@Preview(showBackground = true, name = "Radio · selected · with price", widthDp = 360)
@Composable
private fun AdditionItemRadioSelectedWithPricePreview() {
    FoodDeliveryTheme {
        AdditionRowItem(
            uuid = "1",
            groupId = "group-single",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Extra cheese",
            price = "+ 99 ₽",
            isSelected = true,
            isMultiple = false,
            onCardClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, name = "Radio · not selected", widthDp = 360)
@Composable
private fun AdditionItemRadioUnselectedPreview() {
    FoodDeliveryTheme {
        AdditionRowItem(
            uuid = "2",
            groupId = "group-single",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Bacon",
            price = "+ 149 ₽",
            isSelected = false,
            isMultiple = false,
            onCardClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, name = "Checkbox · selected", widthDp = 360)
@Composable
private fun AdditionItemCheckboxSelectedPreview() {
    FoodDeliveryTheme {
        AdditionRowItem(
            uuid = "3",
            groupId = "group-multi",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Jalapeño",
            price = "+ 49 ₽",
            isSelected = true,
            isMultiple = true,
            onCardClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, name = "Checkbox · not selected", widthDp = 360)
@Composable
private fun AdditionItemCheckboxUnselectedPreview() {
    FoodDeliveryTheme {
        AdditionRowItem(
            uuid = "4",
            groupId = "group-multi",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Olives",
            price = "+ 79 ₽",
            isSelected = false,
            isMultiple = true,
            onCardClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, name = "Radio · no price", widthDp = 360)
@Composable
private fun AdditionItemRadioNoPricePreview() {
    FoodDeliveryTheme {
        AdditionRowItem(
            uuid = "5",
            groupId = "group-single",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Included sauce",
            price = null,
            isSelected = true,
            isMultiple = false,
            onCardClick = { _, _ -> },
        )
    }
}