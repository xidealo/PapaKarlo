package com.bunbeauty.papakarlo.compose.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.bunbeauty.papakarlo.compose.custom.CountPicker
import com.bunbeauty.papakarlo.compose.element.OverflowingText
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.mediumRoundedCornerShape
import com.bunbeauty.papakarlo.feature.consumer_cart.CartProductItemModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartProductItem(
    modifier: Modifier = Modifier,
    cartProductItemModel: CartProductItemModel,
    onCountIncreased: () -> Unit,
    onCountDecreased: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeightIn(min = FoodDeliveryTheme.dimensions.cardHeight)
            .shadow(
                elevation = FoodDeliveryTheme.dimensions.elevation,
                shape = mediumRoundedCornerShape
            )
            .clip(mediumRoundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .height(FoodDeliveryTheme.dimensions.productImageSmallHeight)
                    .width(FoodDeliveryTheme.dimensions.productImageSmallWidth),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartProductItemModel.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.Inside
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(FoodDeliveryTheme.dimensions.smallSpace)
            ) {
                OverflowingText(
                    text = cartProductItemModel.name,
                    style = FoodDeliveryTheme.typography.h3,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace)) {
                    cartProductItemModel.oldCost?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = cartProductItemModel.oldCost,
                            style = FoodDeliveryTheme.typography.body2,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = cartProductItemModel.newCost,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
            }
            CountPicker(
                modifier = Modifier
                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace)
                    .align(CenterVertically),
                count = cartProductItemModel.count,
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
        cartProductItemModel = CartProductItemModel(
            uuid = "",
            name = "Бэргер",
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
        cartProductItemModel = CartProductItemModel(
            uuid = "",
            name = "Бэргер",
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
