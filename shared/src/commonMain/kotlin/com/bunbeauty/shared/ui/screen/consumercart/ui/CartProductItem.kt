package com.bunbeauty.shared.ui.screen.consumercart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.OverflowingText
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.shared.ui.screen.consumercart.state.ConsumerCartViewState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.description_product

@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    cartProductItem: ConsumerCartViewState.CartProductItemUi,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RectangleShape,
        elevated = false,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 8.dp),
        ) {
            FoodDeliveryAsyncImage(
                modifier =
                    Modifier
                        .heightIn(max = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                        .width(FoodDeliveryTheme.dimensions.productImageSmallWidth)
                        .clip(RoundedCornerShape(8.dp)),
                photoLink = cartProductItem.photoLink,
                contentDescription = stringResource(Res.string.description_product),
                contentScale = ContentScale.FillHeight,
            )
            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = FoodDeliveryTheme.dimensions.smallSpace),
            ) {
                OverflowingText(
                    text = cartProductItem.name,
                    style = FoodDeliveryTheme.typography.titleSmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    maxLines = 2,
                )
                Row(
                    modifier =
                        Modifier
                            .padding(top = 4.dp),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .weight(1f),
                    ) {
                        cartProductItem.additions?.let { additions ->
                            Text(
                                modifier =
                                    Modifier
                                        .padding(
                                            end = FoodDeliveryTheme.dimensions.smallSpace,
                                        ),
                                text = additions,
                                style = FoodDeliveryTheme.typography.bodySmall,
                                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            )
                        }
                        Row(
                            modifier =
                                Modifier
                                    .padding(top = 4.dp),
                        ) {
                            cartProductItem.oldCost?.let { oldCost ->
                                Text(
                                    modifier =
                                        Modifier
                                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                    text = oldCost,
                                    style = FoodDeliveryTheme.typography.bodySmall,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                                )
                            }
                            Text(
                                modifier =
                                    Modifier
                                        .weight(1f),
                                text = cartProductItem.newCost,
                                style = FoodDeliveryTheme.typography.bodySmall.bold,
                                color = FoodDeliveryTheme.colors.mainColors.onSurface,
                            )
                        }
                    }

                    CountPicker(
                        modifier =
                            Modifier
                                .align(CenterVertically),
                        count = cartProductItem.count,
                        onCountIncreased = onCountIncreased,
                        onCountDecreased = onCountDecreased,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CartProductItemPreview() {
    FoodDeliveryTheme {
        CartProductItem(
            cartProductItem =
                ConsumerCartViewState.CartProductItemUi(
                    key = "",
                    uuid = "",
                    name = "Бургер MINI с говядиной и плавленым сыром",
                    newCost = "99 ₽",
                    oldCost = "100 ₽",
                    photoLink = "",
                    count = 5,
                    additions = "Обычная булка • Добавка 1 • Добавка 2",
                    isLast = false,
                ),
            onCountIncreased = {},
            onCountDecreased = {},
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun CartProductItemWithoutOldCostPreview() {
    FoodDeliveryTheme {
        CartProductItem(
            cartProductItem =
                ConsumerCartViewState.CartProductItemUi(
                    key = "",
                    uuid = "",
                    name = "Бургер MINI",
                    newCost = "99 ₽",
                    oldCost = null,
                    photoLink = "",
                    count = 5,
                    additions = "",
                    isLast = false,
                ),
            onCountIncreased = {},
            onCountDecreased = {},
            onClick = {},
        )
    }
}
