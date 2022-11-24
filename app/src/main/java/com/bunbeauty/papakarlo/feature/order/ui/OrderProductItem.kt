package com.bunbeauty.papakarlo.feature.order.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.card
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.order.model.OrderProductItem

@Composable
fun OrderProductItem(
    modifier: Modifier = Modifier,
    orderProductItem: OrderProductItem
) {
    Card(modifier = modifier.card(true)) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .heightIn(min = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(orderProductItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                OverflowingText(
                    text = orderProductItem.name,
                    style = FoodDeliveryTheme.typography.h3,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    orderProductItem.oldPrice?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = orderProductItem.oldPrice,
                            style = FoodDeliveryTheme.typography.body2,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItem.newPrice,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItem.count,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        orderProductItem.oldCost?.let {
                            Text(
                                modifier = Modifier.padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = orderProductItem.oldCost,
                                style = FoodDeliveryTheme.typography.body2,
                                textDecoration = TextDecoration.LineThrough,
                                color = FoodDeliveryTheme.colors.onSurfaceVariant,
                            )
                        }
                        Text(
                            text = orderProductItem.newCost,
                            style = FoodDeliveryTheme.typography.body2,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderProductItemPreview() {
    OrderProductItem(
        orderProductItem = OrderProductItem(
            uuid = "",
            name = "Бэргер с вкусной свинкой ням ням ням ням",
            newPrice = "50 ₽",
            oldPrice = "100 ₽",
            newCost = "100 ₽",
            oldCost = "200 ₽",
            photoLink = "",
            count = "× 2"
        )
    )
}

@Preview
@Composable
private fun OrderProductItemWithoutOldPricePreview() {
    OrderProductItem(
        orderProductItem = OrderProductItem(
            uuid = "",
            name = "Бэргер с вкусной свинкой ням ням ням ням",
            newPrice = "50 ₽",
            oldPrice = null,
            newCost = "100 ₽",
            oldCost = null,
            photoLink = "",
            count = "× 2"
        )
    )
}
