package com.bunbeauty.designsystem.ui.element.addition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
fun AdditionCardItem(
    uuid: String,
    groupId: String,
    photoLink: String,
    name: String,
    price: String?,
    isSelected: Boolean,
    isMultiple: Boolean,
    onCardClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FoodDeliveryCard(
        onClick = {
            onCardClick(uuid, groupId)
        },
        elevated = false,
        modifier = modifier.size(
            height = 166.dp,
            width = 100.dp
        ),
        shape = RoundedCornerShape(size = 24.dp),
        colors = FoodDeliveryCardDefaults.surfaceVariateCardColors,
        border =
            if (isSelected) {
                BorderStroke(
                    width = 2.dp,
                    color = FoodDeliveryTheme.colors.mainColors.primary,
                )
            } else {
                null
            },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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

            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = name,
                    style = FoodDeliveryTheme.typography.bodySmall,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }

            price?.let { price ->
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                    textAlign = TextAlign.Center,
                    text = price,
                    style = FoodDeliveryTheme.typography.bodyLarge,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
            }

            if (isMultiple) {
                FoodDeliveryCheckbox(
                    modifier = Modifier.padding(vertical = 8.dp),
                    checked = isSelected,
                    onCheckedChange = {
                        onCardClick(uuid, groupId)
                    },
                )
            } else {
                FoodDeliveryRadioButton(
                    modifier = Modifier.padding(vertical = 8.dp),
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

@Preview(showBackground = true, name = "Radio · selected · with price")
@Composable
private fun AdditionItemRadioSelectedWithPricePreview() {
    FoodDeliveryTheme {
        AdditionCardItem(
            uuid = "1",
            groupId = "group-single",
            photoLink = PREVIEW_PHOTO_LINK,
            name = "Максимальноеколичествосимволов1234",
            price = "+ 99 ₽",
            isSelected = true,
            isMultiple = false,
            onCardClick = { _, _ -> },
        )
    }
}

@Preview(showBackground = true, name = "Radio · not selected")
@Composable
private fun AdditionItemRadioUnselectedPreview() {
    FoodDeliveryTheme {
        AdditionCardItem(
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

@Preview(showBackground = true, name = "Checkbox · selected")
@Composable
private fun AdditionItemCheckboxSelectedPreview() {
    FoodDeliveryTheme {
        AdditionCardItem(
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

@Preview(showBackground = true, name = "Checkbox · not selected")
@Composable
private fun AdditionItemCheckboxUnselectedPreview() {
    FoodDeliveryTheme {
        AdditionCardItem(
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

@Preview(showBackground = true, name = "Radio · no price")
@Composable
private fun AdditionItemRadioNoPricePreview() {
    FoodDeliveryTheme {
        AdditionCardItem(
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