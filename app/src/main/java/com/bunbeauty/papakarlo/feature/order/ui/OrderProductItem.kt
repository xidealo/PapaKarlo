package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.order.screen.orderdetails.OrderProductUiItem

@Composable
fun OrderProductItem(
    modifier: Modifier = Modifier,
    orderProductItem: OrderProductUiItem,
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        clickable = false,
        elevated = false
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(orderProductItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_small),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                OverflowingText(
                    text = orderProductItem.name,
                    style = FoodDeliveryTheme.typography.titleSmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    maxLines = 2
                )

                orderProductItem.additions?.let { additions ->
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = additions,
                        style = FoodDeliveryTheme.typography.bodySmall,
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                    )
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    orderProductItem.oldPrice?.let { oldPrice ->
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = oldPrice,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItem.newPrice,
                        style = FoodDeliveryTheme.typography.bodySmall.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItem.count,
                        style = FoodDeliveryTheme.typography.bodySmall,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        orderProductItem.oldCost?.let { oldCost ->
                            Text(
                                modifier = Modifier.padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = oldCost,
                                style = FoodDeliveryTheme.typography.bodySmall,
                                textDecoration = TextDecoration.LineThrough,
                                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                            )
                        }
                        Text(
                            text = orderProductItem.newCost,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OrderProductItemPreview() {
    FoodDeliveryTheme {
        OrderProductItem(
            orderProductItem = OrderProductUiItem(
                uuid = "",
                name = "Бэргер с вкусной свинкой ням ням ням ням",
                newPrice = "50 ₽",
                oldPrice = "100 ₽",
                newCost = "100 ₽",
                oldCost = "200 ₽",
                photoLink = "",
                count = "× 2",
                key = "uuid",
                additions = "Необычный лаваш • Добавка 1 • Добавка 2 Необычный лаваш • Добавка 1 • Добавка 2 Необычный лаваш • Добавка 1 • Добавка 2"
            )
        )
    }
}

@Preview
@Composable
private fun OrderProductItemWithoutOldPricePreview() {
    FoodDeliveryTheme {
        OrderProductItem(
            orderProductItem = OrderProductUiItem(
                uuid = "",
                name = "Бэргер с вкусной свинкой ням ням ням ням",
                newPrice = "50 ₽",
                oldPrice = null,
                newCost = "100 ₽",
                oldCost = null,
                photoLink = "",
                count = "× 2",
                key = "uuid",
                additions = "Необычный лаваш • Добавка 1 • Добавка 2"
            )
        )
    }
}
