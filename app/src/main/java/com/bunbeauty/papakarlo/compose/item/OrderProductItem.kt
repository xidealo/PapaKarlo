package com.bunbeauty.papakarlo.compose.item

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.compose.card
import com.bunbeauty.papakarlo.compose.element.OverflowingText
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductItemModel

@Composable
fun OrderProductItem(
    modifier: Modifier = Modifier,
    orderProductItemModel: OrderProductItemModel
) {
    Card(modifier = modifier.card(true)) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .heightIn(min = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(orderProductItemModel.photoLink)
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
                    text = orderProductItemModel.name,
                    style = FoodDeliveryTheme.typography.h3,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    orderProductItemModel.oldPrice?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = orderProductItemModel.oldPrice,
                            style = FoodDeliveryTheme.typography.body2,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItemModel.newPrice,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                        text = orderProductItemModel.count,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        orderProductItemModel.oldCost?.let {
                            Text(
                                modifier = Modifier.padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = orderProductItemModel.oldCost,
                                style = FoodDeliveryTheme.typography.body2,
                                textDecoration = TextDecoration.LineThrough,
                                color = FoodDeliveryTheme.colors.onSurfaceVariant,
                            )
                        }
                        Text(
                            text = orderProductItemModel.newCost,
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
        orderProductItemModel = OrderProductItemModel(
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
        orderProductItemModel = OrderProductItemModel(
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