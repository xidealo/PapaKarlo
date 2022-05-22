package com.bunbeauty.papakarlo.feature.menu.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.bunbeauty.papakarlo.common.ui.element.SmallButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.menu.model.MenuProductItem

@Composable
fun MenuProductItem(
    modifier: Modifier = Modifier,
    menuProductItem: MenuProductItem,
    onButtonClicked: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .card(true)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick
            ),
        backgroundColor = FoodDeliveryTheme.colors.surface
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuProductItem.photoLink)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = stringResource(R.string.description_product),
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.smallSpace)) {
                OverflowingText(
                    text = menuProductItem.name,
                    style = FoodDeliveryTheme.typography.h3,
                    color = FoodDeliveryTheme.colors.onSurface
                )
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.verySmallSpace)) {
                    menuProductItem.oldPrice?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.verySmallSpace),
                            text = menuProductItem.oldPrice,
                            style = FoodDeliveryTheme.typography.body2,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = menuProductItem.newPrice,
                        style = FoodDeliveryTheme.typography.body2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                }
                SmallButton(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    textStringId = R.string.action_product_want,
                    hasShadow = false,
                    onClick = onButtonClicked
                )
            }
        }
    }
}

@Preview(widthDp = 200)
@Composable
private fun MenuProductItemPreview() {
    MenuProductItem(
        menuProductItem = MenuProductItem(
            uuid = "",
            photoLink = "",
            name = "Бэргер",
            newPrice = "99 ₽",
            oldPrice = "100 ₽",
        ),
        onButtonClicked = {}
    ) {}
}