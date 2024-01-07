package com.bunbeauty.papakarlo.feature.consumercart

import android.os.Bundle
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseSingleStateComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
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
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.feature.consumercart.ConsumerCartFragmentDirections.toCreateOrderFragment
import com.bunbeauty.papakarlo.feature.consumercart.ConsumerCartFragmentDirections.toLoginFragment
import com.bunbeauty.papakarlo.feature.consumercart.ConsumerCartFragmentDirections.toMenuFragment
import com.bunbeauty.papakarlo.feature.consumercart.ConsumerCartFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.feature.consumercart.ui.CartProductItem
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragment
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bunbeauty.shared.presentation.menu.MenuProductItem as MenuProductItemModel

class ConsumerCartFragment :
    BaseSingleStateComposeFragment<ConsumerCart.ViewDataState, ConsumerCart.Action, ConsumerCart.Event>() {

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(ProductDetailsFragment.EDIT_REQUEST_KEY) { key, bundle ->
            (activity as? IMessageHost)?.showInfoMessage(
                resources.getString(
                    R.string.msg_menu_product_edited,
                    bundle.getString(
                        ProductDetailsFragment.MENU_PRODUCT_NAME
                    )
                )
            )
        }

        viewModel.onAction(ConsumerCart.Action.Init)
    }

    @Composable
    override fun Screen(
        viewState: ConsumerCart.ViewDataState,
        onAction: (ConsumerCart.Action) -> Unit
    ) {
        FoodDeliveryScaffold(
            title = stringResource(id = R.string.title_cart),
            backActionClick = {
                onAction(ConsumerCart.Action.BackClick)
            },

            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
        ) {
            Crossfade(targetState = viewState.screenState, label = "ConsumerCart") { screenState ->
                when (screenState) {
                    ConsumerCart.ViewDataState.ScreenState.LOADING -> LoadingScreen()
                    ConsumerCart.ViewDataState.ScreenState.SUCCESS -> {
                        viewState.consumerCartData?.let { consumerCartData ->
                            ConsumerCartSuccessScreen(
                                consumerCartData = consumerCartData,
                                onAction = onAction
                            )
                        }
                    }

                    ConsumerCart.ViewDataState.ScreenState.EMPTY -> {
                        EmptyScreen(
                            imageId = R.drawable.ic_cart_24,
                            imageDescriptionId = R.string.description_consumer_cart_empty,
                            mainTextId = R.string.title_consumer_cart_empty,
                            extraTextId = R.string.msg_consumer_cart_empty,
                            buttonTextId = R.string.action_consumer_cart_menu,
                            onClick = {
                                onAction(ConsumerCart.Action.OnMenuClick)
                            }
                        )
                    }

                    ConsumerCart.ViewDataState.ScreenState.ERROR -> {
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

    override fun handleEvent(event: ConsumerCart.Event) {
        when (event) {
            ConsumerCart.Event.NavigateToMenu -> {
                findNavController()
                    .navigateSafe(toMenuFragment())
            }

            ConsumerCart.Event.NavigateToCreateOrder -> {
                findNavController()
                    .navigateSafe(toCreateOrderFragment())
            }

            is ConsumerCart.Event.NavigateToLogin -> {
                findNavController()
                    .navigateSafe(toLoginFragment(SuccessLoginDirection.TO_CREATE_ORDER))
            }

            is ConsumerCart.Event.NavigateToProduct -> {
                findNavController()
                    .navigateSafe(
                        toProductFragment(
                            event.uuid,
                            event.name,
                            event.productDetailsOpenedFrom,
                            event.additionUuidList.toTypedArray(),
                            event.cartProductUuid
                        )
                    )
            }

            ConsumerCart.Event.NavigateBack -> findNavController().popBackStack()
        }
    }

    @Composable
    private fun ConsumerCartSuccessScreen(
        consumerCartData: ConsumerCart.ViewDataState.ConsumerCartData,
        onAction: (ConsumerCart.Action) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = FoodDeliveryTheme.dimensions.mediumSpace),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        key = R.string.msg_consumer_cart_free_delivery_from
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace)
                                .padding(horizontal = 16.dp),
                            text = stringResource(
                                R.string.msg_consumer_cart_free_delivery_from,
                                consumerCartData.forFreeDelivery
                            ),
                            style = FoodDeliveryTheme.typography.bodyMedium,
                            color = FoodDeliveryTheme.colors.mainColors.onBackground
                        )
                    }

                    itemsIndexed(
                        items = consumerCartData.cartProductList,
                        key = { _, cartProductItem -> cartProductItem.uuid },
                        span = { _, _ -> GridItemSpan(maxLineSpan) }
                    ) { index, cartProductItem ->
                        FoodDeliveryItem(needDivider = index != consumerCartData.cartProductList.lastIndex) {
                            CartProductItem(
                                cartProductItem = cartProductItem,
                                onCountIncreased = {
                                    onAction(
                                        ConsumerCart.Action.AddProductToCartClick(
                                            menuProductUuid = cartProductItem.menuProductUuid,
                                            additionUuidList = cartProductItem.additionUuidList
                                        )
                                    )
                                },
                                onCountDecreased = {
                                    onAction(
                                        ConsumerCart.Action.RemoveProductFromCartClick(
                                            menuProductUuid = cartProductItem.menuProductUuid,
                                            cartProductUuid = cartProductItem.uuid
                                        )
                                    )
                                },
                                onClick = {
                                    onAction(
                                        ConsumerCart.Action.OnProductClick(
                                            cartProductItem = cartProductItem
                                        )
                                    )
                                }
                            )
                        }
                    }

                    if (consumerCartData.recommendations.isNotEmpty()) {
                        item(
                            span = { GridItemSpan(maxLineSpan) },
                            key = R.string.msg_consumer_cart_recommendations
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
                        items = consumerCartData.recommendations,
                        key = { _, recommendation -> recommendation.uuid },
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
                            onAddProductClick = {
                                onAction(
                                    ConsumerCart.Action.AddRecommendationProductToCartClick(
                                        menuProductUuid = recommendation.uuid
                                    )
                                )
                            },
                            onProductClick = {
                                onAction(
                                    ConsumerCart.Action.RecommendationClick(
                                        menuProductUuid = recommendation.uuid,
                                        name = recommendation.name
                                    )
                                )
                            }
                        )
                    }
                }
            }

            FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    consumerCartData.firstOrderDiscount?.let { discount ->
                        Row(modifier = Modifier.padding(bottom = 8.dp)) {
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
                        consumerCartData.oldTotalCost?.let { oldTotalCost ->
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
                            text = consumerCartData.newTotalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                    MainButton(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_consumer_cart_creeate_order,
                        onClick = {
                            onAction(ConsumerCart.Action.OnCreateOrderClick)
                        }
                    )
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartSuccessScreenPreview() {
        fun getCartProductItemModel(uuid: String) = CartProductItem(
            uuid = uuid,
            name = "Бэргер",
            newCost = "300 ₽",
            oldCost = "330 ₽",
            photoLink = "",
            count = 3,
            menuProductUuid = "",
            additions = null,
            additionUuidList = emptyList()
        )

        fun getMenuProductItem(uuid: String) = MenuProductItemModel(
            uuid = uuid,
            photoLink = "",
            name = "Бэргер",
            newPrice = 99,
            oldPrice = 100,
            hasAdditions = true
        )

        FoodDeliveryTheme {
            Screen(
                viewState = ConsumerCart.ViewDataState(
                    consumerCartData = ConsumerCart.ViewDataState.ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(
                            getCartProductItemModel("1"),
                            getCartProductItemModel("2"),
                            getCartProductItemModel("3"),
                            getCartProductItemModel("4"),
                            getCartProductItemModel("5")
                        ),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = listOf(
                            getMenuProductItem("6"),
                            getMenuProductItem("7")
                        )
                    ),
                    screenState = ConsumerCart.ViewDataState.ScreenState.SUCCESS
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartEmptyScreenPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = ConsumerCart.ViewDataState(
                    consumerCartData = ConsumerCart.ViewDataState.ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCart.ViewDataState.ScreenState.EMPTY
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartLoadingScreenPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = ConsumerCart.ViewDataState(
                    consumerCartData = ConsumerCart.ViewDataState.ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCart.ViewDataState.ScreenState.LOADING
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartErrorScreenPreview() {
        FoodDeliveryTheme {
            Screen(
                viewState = ConsumerCart.ViewDataState(
                    consumerCartData = ConsumerCart.ViewDataState.ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCart.ViewDataState.ScreenState.ERROR
                ),
                onAction = {}
            )
        }
    }
}
