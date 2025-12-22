package com.bunbeauty.shared.ui.screen.menu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.OverflowingText
import com.bunbeauty.designsystem.ui.element.button.SmallButton
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.shared.ui.screen.menu.state.MenuItemUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.action_product_want
import papakarlo.shared.generated.resources.description_product

@Composable
fun MenuProductItem(
    modifier: Modifier = Modifier,
    menuProductItem: MenuItemUi.Product,
    onAddProductClick: (String) -> Unit,
    onProductClick: (String) -> Unit,
) {
    FoodDeliveryCard(
        modifier = modifier,
        onClick = {
            onProductClick(menuProductItem.uuid)
        },
    ) {
        Column {
            FoodDeliveryAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                photoLink = menuProductItem.photoLink,
                contentDescription = stringResource(Res.string.description_product),
                contentScale = ContentScale.FillWidth,
            )
            Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.smallSpace)) {
                OverflowingText(
                    text = menuProductItem.name,
                    style = FoodDeliveryTheme.typography.titleSmall.bold,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface,
                )
                Row(modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.verySmallSpace)) {
                    menuProductItem.oldPrice?.let { oldPrice ->
                        Text(
                            modifier =
                                Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.verySmallSpace),
                            text = oldPrice,
                            style = FoodDeliveryTheme.typography.bodySmall,
                            textDecoration = TextDecoration.LineThrough,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        )
                    }
                    Text(
                        text = menuProductItem.newPrice,
                        style = FoodDeliveryTheme.typography.bodySmall.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )
                }
                SmallButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = FoodDeliveryTheme.dimensions.smallSpace),
                    textStringId = Res.string.action_product_want,
                    elevated = false,
                    onClick = {
                        onAddProductClick(menuProductItem.uuid)
                    },
                )
            }
        }
    }
}

@Preview(widthDp = 200)
@Composable
private fun MenuProductItemPreview() {
    FoodDeliveryTheme {
        MenuProductItem(
            menuProductItem =
                MenuItemUi.Product(
                    uuid = "",
                    key = "",
                    photoLink = "",
                    name = "Бэргер",
                    newPrice = "99 ₽",
                    oldPrice = "100 ₽",
                ),
            onAddProductClick = {},
            onProductClick = {},
        )
    }
}
