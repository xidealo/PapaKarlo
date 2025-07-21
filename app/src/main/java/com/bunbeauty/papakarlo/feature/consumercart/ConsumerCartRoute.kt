package com.bunbeauty.papakarlo.feature.consumercart

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.DiscountCard
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryItem
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.ui.theme.medium
import com.bunbeauty.papakarlo.feature.consumercart.mapper.toConsumerCartViewState
import com.bunbeauty.papakarlo.feature.consumercart.state.ConsumerCartViewState
import com.bunbeauty.papakarlo.feature.consumercart.ui.CartProductItem
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.menu.state.MenuItemUi
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.motivation.Motivation
import com.bunbeauty.papakarlo.feature.motivation.MotivationUi
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

private const val ANIMATION_LABEL = "ConsumerCartFragment"
private const val ANIMATION_DURATION_MILLIS = 200

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
        cartProductUuid: String?
    ) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(ConsumerCart.Action.Init)
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()
    val onAction = remember {
        { event: ConsumerCart.Action ->
            viewModel.onAction(event)
        }
    }
    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
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
        goToProductFragment = goToProductFragment
    )
    ConsumerCartScreen(viewState = viewState.toConsumerCartViewState(), onAction = onAction)
}

@Composable
fun ConsumerCartScreen(
    viewState: ConsumerCartViewState,
    onAction: (ConsumerCart.Action) -> Unit
) {
    FoodDeliveryScaffold(
        title = stringResource(id = R.string.title_cart),
        backActionClick = {
            onAction(ConsumerCart.Action.BackClick)
        },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
    ) {
        AnimatedContent(
            targetState = viewState,
            label = ANIMATION_LABEL,
            contentKey = { state ->
                state::class.java
            },
            transitionSpec = {
                ContentTransform(
                    targetContentEnter = fadeIn(
                        animationSpec = tween(delayMillis = ANIMATION_DURATION_MILLIS)
                    ),
                    initialContentExit = fadeOut(
                        animationSpec = tween(delayMillis = ANIMATION_DURATION_MILLIS)
                    )
                )
            }
        ) { viewState ->
            when (viewState) {
                ConsumerCartViewState.Loading -> LoadingScreen()

                is ConsumerCartViewState.Success -> {
                    ConsumerCartSuccessScreen(
                        viewState = viewState,
                        onAction = onAction
                    )
                }

                ConsumerCartViewState.Error -> {
                    ErrorScreen(
                        mainTextId = R.string.error_consumer_cart_loading,
                        onClick = {
                            onAction(ConsumerCart.Action.OnErrorButtonClick)
                        }
                    )
                }
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
        cartProductUuid: String?
    ) -> Unit
) {
    val activity = LocalActivity.current
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
                        effect.cartProductUuid
                    )
                }

                ConsumerCart.Event.NavigateBack -> back()
                ConsumerCart.Event.ShowAddProductError -> (activity as? IMessageHost)?.showErrorMessage(
                    activity.resources.getString(R.string.error_consumer_cart_add_product)
                )

                ConsumerCart.Event.ShowRemoveProductError -> (activity as? IMessageHost)?.showErrorMessage(
                    activity.resources.getString(R.string.error_consumer_cart_remove_product)
                )
            }
        }
        consumerEffects()
    }
}

@Composable
private fun ConsumerCartSuccessScreen(
    viewState: ConsumerCartViewState.Success,
    onAction: (ConsumerCart.Action) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = viewState.cartProductList,
                key = { cartProductItem -> cartProductItem.key },
                span = { _ -> GridItemSpan(maxLineSpan) }
            ) { cartProductItem ->
                FoodDeliveryItem(needDivider = !cartProductItem.isLast) {
                    CartProductItem(
                        cartProductItem = cartProductItem,
                        onCountIncreased = {
                            onAction(
                                ConsumerCart.Action.AddProductToCartClick(
                                    cartProductUuid = cartProductItem.uuid
                                )
                            )
                        },
                        onCountDecreased = {
                            onAction(
                                ConsumerCart.Action.RemoveProductFromCartClick(
                                    cartProductUuid = cartProductItem.uuid
                                )
                            )
                        },
                        onClick = {
                            onAction(
                                ConsumerCart.Action.OnCartProductClick(
                                    cartProductUuid = cartProductItem.uuid
                                )
                            )
                        }
                    )
                }
            }

            if (viewState.cartProductList.isEmpty()) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    key = "Empty screen"
                ) {
                    EmptyScreen(
                        imageId = R.drawable.ic_cart_24,
                        imageDescriptionId = R.string.description_consumer_cart_empty,
                        mainTextId = R.string.title_consumer_cart_empty,
                        extraTextId = R.string.msg_consumer_cart_empty
                    )
                }
            }

            recommendationItems(
                recommendationList = viewState.recommendationList,
                onAction = onAction
            )
        }

        BottomPanel(
            bottomPanelInfo = viewState.bottomPanelInfo,
            onAction = onAction
        )
    }
}

@Composable
private fun BottomPanel(
    bottomPanelInfo: ConsumerCartViewState.BottomPanelInfoUi?,
    onAction: (ConsumerCart.Action) -> Unit,
    modifier: Modifier = Modifier
) {
    FoodDeliverySurface(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = spacedBy(8.dp)
        ) {
            if (bottomPanelInfo == null) {
                MainButton(
                    textStringId = R.string.action_consumer_cart_menu,
                    onClick = {
                        onAction(ConsumerCart.Action.OnMenuClick)
                    }
                )
            } else {
                Motivation(bottomPanelInfo.motivation)
                bottomPanelInfo.discount?.let { discount ->
                    Row {
                        Text(
                            text = stringResource(R.string.title_consumer_cart_discount),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        DiscountCard(discount = discount)
                    }
                }
                Row {
                    Text(
                        text = stringResource(R.string.title_consumer_cart_total),
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    bottomPanelInfo.oldTotalCost?.let { oldTotalCost ->
                        Text(
                            modifier = Modifier
                                .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                            text = oldTotalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = bottomPanelInfo.newTotalCost,
                        style = FoodDeliveryTheme.typography.bodyMedium.bold,
                        color = FoodDeliveryTheme.colors.mainColors.onSurface
                    )
                }
                MainButton(
                    modifier = Modifier.padding(top = 8.dp),
                    textStringId = R.string.action_consumer_cart_creeate_order,
                    onClick = {
                        onAction(ConsumerCart.Action.OnCreateOrderClick)
                    },
                    enabled = bottomPanelInfo.orderAvailable
                )
            }
        }
    }
}

private fun LazyGridScope.recommendationItems(
    recommendationList: ImmutableList<MenuItemUi.Product>,
    onAction: (ConsumerCart.Action) -> Unit
) {
    if (recommendationList.isNotEmpty()) {
        item(
            span = { GridItemSpan(maxLineSpan) },
            key = "Recommendations"
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                text = stringResource(
                    R.string.msg_consumer_cart_recommendations
                ),
                style = FoodDeliveryTheme.typography.titleMedium.medium,
                color = FoodDeliveryTheme.colors.mainColors.onSurface
            )
        }
    }
    itemsIndexed(
        items = recommendationList,
        key = { _, recommendation -> recommendation.key },
        span = { _, _ -> GridItemSpan(1) }
    ) { index, recommendation ->
        MenuProductItem(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = if (index % 2 == 0) {
                        16.dp
                    } else {
                        0.dp
                    },
                    end = if (index % 2 == 1) {
                        16.dp
                    } else {
                        0.dp
                    }
                ),
            menuProductItem = recommendation,
            onAddProductClick = { menuProductUuid ->
                onAction(
                    ConsumerCart.Action.AddRecommendationProductToCartClick(
                        menuProductUuid = menuProductUuid
                    )
                )
            },
            onProductClick = { menuProductUuid ->
                onAction(
                    ConsumerCart.Action.RecommendationClick(
                        menuProductUuid = menuProductUuid
                    )
                )
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ConsumerCartSuccessScreenPreview() {
    fun getCartProductItemModel(uuid: String) = ConsumerCartViewState.CartProductItemUi(
        key = uuid,
        uuid = uuid,
        name = "Бэргер",
        newCost = "300 ₽",
        oldCost = "330 ₽",
        photoLink = "",
        count = 3,
        additions = null,
        isLast = false
    )

    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Success(
                cartProductList = persistentListOf(
                    getCartProductItemModel("1"),
                    getCartProductItemModel("2"),
                    getCartProductItemModel("3"),
                    getCartProductItemModel("4"),
                    getCartProductItemModel("5")
                ),
                bottomPanelInfo = ConsumerCartViewState.BottomPanelInfoUi(
                    motivation = MotivationUi.ForLowerDelivery(
                        increaseAmountBy = "550 ₽",
                        progress = 0.5f,
                        isFree = true
                    ),
                    discount = "10%",
                    oldTotalCost = "1650 ₽",
                    newTotalCost = "1500 ₽",
                    orderAvailable = true
                ),
                recommendationList = persistentListOf(
                    getRecommendation("6"),
                    getRecommendation("7")
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ConsumerCartEmptyScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Success(
                cartProductList = persistentListOf(),
                bottomPanelInfo = null,
                recommendationList = persistentListOf(
                    getRecommendation("1"),
                    getRecommendation("2")
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ConsumerCartLoadingScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Loading,
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ConsumerCartErrorScreenPreview() {
    FoodDeliveryTheme {
        ConsumerCartScreen(
            viewState = ConsumerCartViewState.Error,
            onAction = {}
        )
    }
}

private fun getRecommendation(uuid: String) = MenuItemUi.Product(
    uuid = uuid,
    key = uuid,
    photoLink = "",
    name = "Бэргер",
    newPrice = "99 ₽",
    oldPrice = "100 ₽"
)
