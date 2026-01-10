package com.bunbeauty.order.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.core.model.SuccessLoginDirection
import com.bunbeauty.core.motivation.Motivation
import com.bunbeauty.core.motivation.MotivationUi
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAsyncImage
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.OverflowingText
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.element.button.SmallButton
import com.bunbeauty.designsystem.ui.element.card.DiscountCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryItem
import com.bunbeauty.designsystem.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.designsystem.ui.screen.EmptyScreen
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.order.presentation.consumercart.ConsumerCart
import com.bunbeauty.order.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.order.ui.mapper.toConsumerCartViewState
import com.bunbeauty.order.ui.state.ConsumerCartViewState
import com.bunbeauty.order.ui.ui.CartProductItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.action_consumer_cart_creeate_order
import papakarlo.designsystem.generated.resources.action_consumer_cart_menu
import papakarlo.designsystem.generated.resources.action_product_want
import papakarlo.designsystem.generated.resources.description_consumer_cart_empty
import papakarlo.designsystem.generated.resources.description_product
import papakarlo.designsystem.generated.resources.error_consumer_cart_add_product
import papakarlo.designsystem.generated.resources.error_consumer_cart_loading
import papakarlo.designsystem.generated.resources.error_consumer_cart_remove_product
import papakarlo.designsystem.generated.resources.ic_cart_24
import papakarlo.designsystem.generated.resources.msg_consumer_cart_empty
import papakarlo.designsystem.generated.resources.msg_consumer_cart_recommendations
import papakarlo.designsystem.generated.resources.title_cart
import papakarlo.designsystem.generated.resources.title_consumer_cart_discount
import papakarlo.designsystem.generated.resources.title_consumer_cart_empty
import papakarlo.designsystem.generated.resources.title_consumer_cart_total

@Composable
fun ConsumerCartRoute(
    viewModel: ConsumerCartViewModel = koinViewModel(),
    back: () -> Unit,
    goToMenuFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    goToLoginFragment: (SuccessLoginDirection) -> Unit,
    goToProductFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        additionUuidList: List<String>,
        cartProductUuid: String?,
    ) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(ConsumerCart.Action.Init)
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()
    val onAction =
        remember {
            { event: ConsumerCart.Action ->
                viewModel.onAction(event)
            }
        }
    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    ConsumerCartEffect(
        effects = effects,
        consumerEffects = consumeEffects,
        back = back,
        goToMenuFragment = goToMenuFragment,
        goToCreateOrderFragment = goToCreateOrderFragment,
        goToLoginFragment = goToLoginFragment,
        goToProductFragment = goToProductFragment,
        showErrorMessage = showErrorMessage,
    )
    ConsumerCartScreen(viewState = viewState.toConsumerCartViewState(), onAction = onAction)
}

@Composable
fun ConsumerCartScreen(
    viewState: ConsumerCartViewState,
    onAction: (ConsumerCart.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        title = stringResource(resource = Res.string.title_cart),
        backActionClick = {
            onAction(ConsumerCart.Action.BackClick)
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
    ) {
        when (viewState) {
            ConsumerCartViewState.Loading -> LoadingScreen()

            is ConsumerCartViewState.Success -> {
                ConsumerCartSuccessScreen(
                    viewState = viewState,
                    onAction = onAction,
                )
            }

            ConsumerCartViewState.Error -> {
                ErrorScreen(
                    mainTextId = Res.string.error_consumer_cart_loading,
                    onClick = {
                        onAction(ConsumerCart.Action.OnErrorButtonClick)
                    },
                )
            }
        }
    }
}

@Composable
fun ConsumerCartEffect(
    effects: List<ConsumerCart.Event>,
    consumerEffects: () -> Unit,
    back: () -> Unit,
    goToMenuFragment: () -> Unit,
    goToCreateOrderFragment: () -> Unit,
    goToLoginFragment: (SuccessLoginDirection) -> Unit,
    goToProductFragment: (
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        additionUuidList: List<String>,
        cartProductUuid: String?,
    ) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                ConsumerCart.Event.NavigateToMenu -> goToMenuFragment()

                ConsumerCart.Event.NavigateToCreateOrder -> goToCreateOrderFragment()

                is ConsumerCart.Event.NavigateToLogin -> goToLoginFragment(SuccessLoginDirection.TO_CREATE_ORDER)

                is ConsumerCart.Event.NavigateToProduct -> {
                    goToProductFragment(
                        effect.uuid,
                        effect.name,
                        effect.productDetailsOpenedFrom,
                        effect.additionUuidList,
                        effect.cartProductUuid,
                    )
                }

                ConsumerCart.Event.NavigateBack -> back()
                ConsumerCart.Event.ShowAddProductError -> {
                    showErrorMessage(getString(Res.string.error_consumer_cart_add_product))
                }

                ConsumerCart.Event.ShowRemoveProductError -> {
                    showErrorMessage(getString(Res.string.error_consumer_cart_remove_product))
                }
            }
        }
        consumerEffects()
    }
}

@Composable
private fun ConsumerCartSuccessScreen(
    viewState: ConsumerCartViewState.Success,
    onAction: (ConsumerCart.Action) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        LazyVerticalGrid(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = viewState.cartProductList,
                key = { cartProductItem -> cartProductItem.key },
                span = { _ -> GridItemSpan(maxLineSpan) },
            ) { cartProductItem ->
                FoodDeliveryItem(needDivider = !cartProductItem.isLast) {
                    CartProductItem(
                        cartProductItem = cartProductItem,
                        onCountIncreased = {
                            onAction(
                                ConsumerCart.Action.AddProductToCartClick(
                                    cartProductUuid = cartProductItem.uuid,
                                ),
                            )
                        },
                        onCountDecreased = {
                            onAction(
                                ConsumerCart.Action.RemoveProductFromCartClick(
                                    cartProductUuid = cartProductItem.uuid,
                                ),
                            )
                        },
                        onClick = {
                            onAction(
                                ConsumerCart.Action.OnCartProductClick(
                                    cartProductUuid = cartProductItem.uuid,
                                ),
                            )
                        },
                    )
                }
            }

            if (viewState.cartProductList.isEmpty()) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    key = "Empty screen",
                ) {
                    EmptyScreen(
                        imageId = Res.drawable.ic_cart_24,
                        imageDescriptionId = Res.string.description_consumer_cart_empty,
                        mainTextId = Res.string.title_consumer_cart_empty,
                        extraTextId = Res.string.msg_consumer_cart_empty,
                    )
                }
            }

            recommendationItems(
                recommendationList = viewState.recommendationList,
                onAction = onAction,
            )
        }

        BottomPanel(
            bottomPanelInfo = viewState.bottomPanelInfo,
            onAction = onAction,
        )
    }
}

@Composable
private fun BottomPanel(
    bottomPanelInfo: ConsumerCartViewState.BottomPanelInfoUi?,
    onAction: (ConsumerCart.Action) -> Unit,
    modifier: Modifier = Modifier,
) {
    FoodDeliverySurface(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = spacedBy(8.dp),
        ) {
            if (bottomPanelInfo == null) {
                MainButton(
                    textStringId = Res.string.action_consumer_cart_menu,
                    onClick = {
                        onAction(ConsumerCart.Action.OnMenuClick)
                    },
                )
            } else {
                Motivation(bottomPanelInfo.motivation)
                bottomPanelInfo.discount?.let { discount ->
                    Row {
                        Text(
                            text = stringResource(Res.string.title_consumer_cart_discount),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        DiscountCard(discount = discount)
                    }
                }
                Row {
                    Text(
                        text = stringResource(Res.string.title_consumer_cart_total),
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    bottomPanelInfo.oldTotalCost?.let { oldTotalCost ->
                        Text(
                            modifier =
                                Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = oldTotalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough,
                        )
                    }
                    Text(
                        text = bottomPanelInfo.newTotalCost,
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface,
                    )
                }
                MainButton(
                    modifier = Modifier.padding(top = 8.dp),
                    textStringId = Res.string.action_consumer_cart_creeate_order,
                    onClick = {
                        onAction(ConsumerCart.Action.OnCreateOrderClick)
                    },
                    enabled = bottomPanelInfo.orderAvailable,
                )
            }
        }
    }
}

private fun LazyGridScope.recommendationItems(
    recommendationList: ImmutableList<ConsumerCartViewState.Success.ProductUi>,
    onAction: (ConsumerCart.Action) -> Unit,
) {
    if (recommendationList.isNotEmpty()) {
        item(
            span = { GridItemSpan(maxLineSpan) },
            key = "Recommendations",
        ) {
            Text(
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                text =
                    stringResource(
                        Res.string.msg_consumer_cart_recommendations,
                    ),
                style = FoodDeliveryTheme.typography.titleMedium.medium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface,
            )
        }
    }
    itemsIndexed(
        items = recommendationList,
        key = { _, recommendation -> recommendation.key },
        span = { _, _ -> GridItemSpan(1) },
    ) { index, recommendation ->
        MenuProductItem(
            modifier =
                Modifier
                    .padding(
                        top = 8.dp,
                        start =
                            if (index % 2 == 0) {
                                16.dp
                            } else {
                                0.dp
                            },
                        end =
                            if (index % 2 == 1) {
                                16.dp
                            } else {
                                0.dp
                            },
                    ),
            menuProductItem = recommendation,
            onAddProductClick = { menuProductUuid ->
                onAction(
                    ConsumerCart.Action.AddRecommendationProductToCartClick(
                        menuProductUuid = menuProductUuid,
                    ),
                )
            },
            onProductClick = { menuProductUuid ->
                onAction(
                    ConsumerCart.Action.RecommendationClick(
                        menuProductUuid = menuProductUuid,
                    ),
                )
            },
        )
    }
}

@Composable
fun MenuProductItem(
    modifier: Modifier = Modifier,
    menuProductItem: ConsumerCartViewState.Success.ProductUi,
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
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                photoLink = menuProductItem.photoLink,
                contentDescription = stringResource(resource = Res.string.description_product),
                contentScale = ContentScale.FillWidth,
            )
            Column(
                modifier =
                    Modifier.padding(
                        all = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
            ) {
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

@Preview(showBackground = true)
@Composable
private fun ConsumerCartSuccessScreenPreview() {
    fun getCartProductItemModel(uuid: String) =
        ConsumerCartViewState.CartProductItemUi(
            key = uuid,
            uuid = uuid,
            name = "Бэргер",
            newCost = "300 ₽",
            oldCost = "330 ₽",
            photoLink = "",
            count = 3,
            additions = null,
            isLast = false,
        )

    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState =
                ConsumerCartViewState.Success(
                    cartProductList =
                        persistentListOf(
                            getCartProductItemModel("1"),
                            getCartProductItemModel("2"),
                            getCartProductItemModel("3"),
                            getCartProductItemModel("4"),
                            getCartProductItemModel("5"),
                        ),
                    bottomPanelInfo =
                        ConsumerCartViewState.BottomPanelInfoUi(
                            motivation =
                                MotivationUi.ForLowerDelivery(
                                    increaseAmountBy = "550 ₽",
                                    progress = 0.5f,
                                    isFree = true,
                                ),
                            discount = "10%",
                            oldTotalCost = "1650 ₽",
                            newTotalCost = "1500 ₽",
                            orderAvailable = true,
                        ),
                    recommendationList =
                        persistentListOf(
                            getRecommendation("6"),
                            getRecommendation("7"),
                        ),
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumerCartEmptyScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState =
                ConsumerCartViewState.Success(
                    cartProductList = persistentListOf(),
                    bottomPanelInfo = null,
                    recommendationList =
                        persistentListOf(
                            getRecommendation("1"),
                            getRecommendation("2"),
                        ),
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumerCartLoadingScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Loading,
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumerCartErrorScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Error,
            onAction = {},
        )
    }
}

private fun getRecommendation(uuid: String) =
    ConsumerCartViewState.Success.ProductUi(
        uuid = uuid,
        key = uuid,
        photoLink = "",
        name = "Бэргер",
        newPrice = "99 ₽",
        oldPrice = "100 ₽",
    )
