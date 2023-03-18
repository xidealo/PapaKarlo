package com.bunbeauty.papakarlo.feature.consumer_cart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryCard
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.consumer_cart.model.CartProductItem

@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    cartProductItem: CartProductItem,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
    onClick: () -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartProductItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                OverflowingText(
                    text = cartProductItem.name,
                    style = FoodDeliveryTheme.typography.titleSmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    maxLines = 1
                )
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                    cartProductItem.oldCost?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = cartProductItem.oldCost,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = cartProductItem.newCost,
                        style = FoodDeliveryTheme.typography.bodySmall.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }
            }
            CountPicker(
                modifier = Modifier
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                    .align(CenterVertically),
                count = cartProductItem.count,
                onCountIncreased = onCountIncreased,
                onCountDecreased = onCountDecreased,
            )
        }
    }
}

@Preview
@Composable
private fun CartProductItemPreview() {
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
    ) {}
}

@Preview
@Composable
private fun CartProductItemWithoutOldCostPreview() {
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
    ) {}
}
