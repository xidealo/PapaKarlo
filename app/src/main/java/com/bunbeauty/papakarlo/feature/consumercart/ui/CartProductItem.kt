package com.bunbeauty.papakarlo.feature.consumercart.ui

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
import com.bunbeauty.shared.presentation.consumercart.CartProductItem

@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    cartProductItem: CartProductItem,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
    onClick: () -> Unit
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevated = false,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartProductItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_small),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                    .padding(start = FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                OverflowingText(
                    text = cartProductItem.name,
                    style = FoodDeliveryTheme.typography.titleSmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace)
                ) {
                    cartProductItem.oldCost?.let { oldCost ->
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = oldCost,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                        )
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = cartProductItem.newCost,
                        style = FoodDeliveryTheme.typography.bodySmall.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )

                    CountPicker(
                        modifier = Modifier
                            .padding()
                            .align(CenterVertically),
                        count = cartProductItem.count,
                        onCountIncreased = onCountIncreased,
                        onCountDecreased = onCountDecreased
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CartProductItemPreview() {
    FoodDeliveryTheme {
        CartProductItem(
            cartProductItem = CartProductItem(
                uuid = "",
                name = "Бургер MINI с говядиной и плавленым сыром",
                newCost = "99 ₽",
                oldCost = "100 ₽",
                photoLink = "",
                count = 5,
                menuProductUuid = ""
            ),
            onCountIncreased = {},
            onCountDecreased = {},
            onClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CartProductItemWithoutOldCostPreview() {
    FoodDeliveryTheme {
        CartProductItem(
            cartProductItem = CartProductItem(
                uuid = "",
                name = "Бургер MINI с говядиной и плавленым сыром",
                newCost = "99 ₽",
                oldCost = null,
                photoLink = "",
                count = 5,
                menuProductUuid = ""
            ),
            onCountIncreased = {},
            onCountDecreased = {},
            onClick = {}
        )
    }
}
