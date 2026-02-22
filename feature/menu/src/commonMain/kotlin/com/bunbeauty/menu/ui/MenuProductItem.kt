package com.bunbeauty.menu.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.OverflowingText
import com.bunbeauty.designsystem.ui.element.button.SmallButton
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.menu.ui.state.MenuItemUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_product_want
import papakarlo.designsystem.generated.resources.description_product

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MenuProductItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    menuProductItem: MenuItemUi.Product,
    onAddProductClick: (String) -> Unit,
    onProductClick: (String) -> Unit,
) {
    with(sharedTransitionScope) {
        FoodDeliveryCard(
            modifier = modifier,
            onClick = {
                onProductClick(menuProductItem.uuid)
            },
            shape = RoundedCornerShape(size = 24.dp),
        ) {
            Column(
                modifier = Modifier.background(FoodDeliveryTheme.colors.mainColors.surfaceVariant),
            ) {
                FoodDeliveryAsyncImage(
                    modifier =
                        Modifier
                            .sharedElement(
                                sharedContentState =
                                    sharedTransitionScope
                                        .rememberSharedContentState(key = "image-${menuProductItem.uuid}"),
                                animatedVisibilityScope = animatedContentScope,
                            ).fillMaxWidth()
                            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .heightIn(min = 120.dp),
                    photoLink = menuProductItem.photoLink,
                    contentDescription = stringResource(Res.string.description_product),
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier =
                        Modifier
                            .padding(FoodDeliveryTheme.dimensions.smallSpace),
                ) {
                    Row(
                        modifier =
                            Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.verySmallSpace,
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        menuProductItem.oldPrice?.let { oldPrice ->
                            Text(
                                modifier =
                                    Modifier
                                        .padding(end = FoodDeliveryTheme.dimensions.verySmallSpace)
                                        .sharedElement(
                                            sharedContentState =
                                                sharedTransitionScope
                                                    .rememberSharedContentState(
                                                        key = "oldPrice-${menuProductItem.uuid}",
                                                    ),
                                            animatedVisibilityScope = animatedContentScope,
                                        ),
                                text = oldPrice,
                                style = FoodDeliveryTheme.typography.bodySmall,
                                textDecoration = TextDecoration.LineThrough,
                                color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            )
                        }
                        Text(
                            modifier =
                                Modifier
                                    .sharedElement(
                                        sharedContentState =
                                            sharedTransitionScope
                                                .rememberSharedContentState(
                                                    key = "price-${menuProductItem.uuid}",
                                                ),
                                        animatedVisibilityScope = animatedContentScope,
                                    ),
                            text = menuProductItem.newPrice,
                            style = FoodDeliveryTheme.typography.bodyLarge.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                        )
                    }

                    OverflowingText(
                        modifier =
                            Modifier
                                .padding(top = 4.dp)
                                .sharedElement(
                                    sharedContentState =
                                        sharedTransitionScope
                                            .rememberSharedContentState(
                                                key = "text-${menuProductItem.uuid}",
                                            ),
                                    animatedVisibilityScope = animatedContentScope,
                                ),
                        text = menuProductItem.name,
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )

                    SmallButton(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
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
}

@Preview(widthDp = 200)
@Composable
private fun MenuProductItemPreview() {
    FoodDeliveryTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
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
                    animatedContentScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout,
                )
            }
        }
    }
}
