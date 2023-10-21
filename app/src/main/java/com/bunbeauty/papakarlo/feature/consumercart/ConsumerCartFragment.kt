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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.card.DiscountCard
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
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartData
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartState
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumerCartFragment :
    BaseComposeFragment<ConsumerCartState.State, ConsumerCartState.Action, ConsumerCartState.Event>() {

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.handleAction(ConsumerCartState.Action.Init)
    }

    @Composable
    override fun Screen(
        state: ConsumerCartState.State,
        onAction: (ConsumerCartState.Action) -> Unit,
    ) {
        FoodDeliveryScaffold(
            title = stringResource(id = R.string.title_cart),
            backActionClick = {
                onAction(ConsumerCartState.Action.BackClick)
            },

            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface
        ) {
            //check if change state to sealed class
            Crossfade(targetState = state.screenState, label = "ConsumerCart") { screenState ->
                when (screenState) {
                    ConsumerCartState.ScreenState.LOADING -> LoadingScreen()
                    ConsumerCartState.ScreenState.SUCCESS -> {
                        //Warning(Check!)
                        state.consumerCartData?.let { consumerCartData ->
                            ConsumerCartSuccessScreen(
                                consumerCartData = consumerCartData,
                                onAction = onAction
                            )
                        }
                    }

                    ConsumerCartState.ScreenState.EMPTY -> {
                        EmptyScreen(
                            imageId = R.drawable.ic_cart_24,
                            imageDescriptionId = R.string.description_consumer_cart_empty,
                            mainTextId = R.string.title_consumer_cart_empty,
                            extraTextId = R.string.msg_consumer_cart_empty,
                            buttonTextId = R.string.action_consumer_cart_menu,
                            onClick = {
                                onAction(ConsumerCartState.Action.OnMenuClick)
                            }
                        )
                    }

                    ConsumerCartState.ScreenState.ERROR -> {
                        ErrorScreen(
                            mainTextId = R.string.error_consumer_cart_loading,
                            onClick = {
                                onAction(ConsumerCartState.Action.OnErrorButtonClick)
                            }
                        )
                    }
                }
            }

        }
    }

    override fun handleEvent(event: ConsumerCartState.Event) {
        when (event) {
            ConsumerCartState.Event.NavigateToMenu -> {
                findNavController()
                    .navigateSafe(toMenuFragment())
            }

            ConsumerCartState.Event.NavigateToCreateOrder -> {
                findNavController()
                    .navigateSafe(toCreateOrderFragment())
            }

            is ConsumerCartState.Event.NavigateToLogin -> {
                findNavController()
                    .navigateSafe(toLoginFragment(SuccessLoginDirection.TO_CREATE_ORDER))
            }

            is ConsumerCartState.Event.NavigateToProduct -> {
                findNavController()
                    .navigateSafe(
                        toProductFragment(
                            event.uuid,
                            event.name
                        )
                    )
            }

            ConsumerCartState.Event.NavigateBack -> findNavController().popBackStack()
        }

    }

    @Composable
    private fun ConsumerCartSuccessScreen(
        consumerCartData: ConsumerCartData,
        onAction: (ConsumerCartState.Action) -> Unit,
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
                    horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp),
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
                            color = FoodDeliveryTheme.colors.mainColors.onBackground,
                        )
                    }

                    itemsIndexed(
                        items = consumerCartData.cartProductList,
                        key = { index, cartProductItem -> cartProductItem.uuid },
                        span = { index, cartProductItem -> GridItemSpan(maxLineSpan) },
                    ) { index, cartProductItem ->
                        Column {
                            CartProductItem(
                                cartProductItem = cartProductItem,
                                onCountIncreased = {
                                    onAction(
                                        ConsumerCartState.Action.AddProductToCartClick(
                                            menuProductUuid = cartProductItem.menuProductUuid
                                        )
                                    )
                                },
                                onCountDecreased = {
                                    onAction(
                                        ConsumerCartState.Action.RemoveProductFromCartClick(
                                            menuProductUuid = cartProductItem.menuProductUuid
                                        )
                                    )
                                },
                                onClick = {
                                    onAction(
                                        ConsumerCartState.Action.OnProductClick(
                                            cartProductItem = cartProductItem
                                        )
                                    )
                                }
                            )
                            if (index != consumerCartData.cartProductList.lastIndex)
                                Divider(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp),
                                    thickness = 1.dp,
                                    color = FoodDeliveryTheme.colors.mainColors.background
                                )
                        }

                    }
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
                            color = FoodDeliveryTheme.colors.mainColors.onSurface,
                        )
                    }
                    itemsIndexed(
                        items = consumerCartData.recommendations,
                        key = { index, recommendation -> recommendation.uuid },
                        span = { index, cartProductItem -> GridItemSpan(1) }
                    ) { index, recommendation ->
                        MenuProductItem(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .padding(horizontal = 16.dp),
                            menuProductItem = recommendation,
                            onAddProductClick = {
                                onAction(
                                    ConsumerCartState.Action.AddProductToRecommendationClick(
                                        menuProductUuid = recommendation.uuid
                                    )
                                )
                            },
                            onProductClick = {
                                onAction(
                                    ConsumerCartState.Action.RecommendationClick(
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
                            onAction(ConsumerCartState.Action.OnCreateOrderClick)
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
            menuProductUuid = ""
        )

        fun getMenuProductItem(uuid: String) =
            com.bunbeauty.shared.presentation.menu.MenuProductItem(
                uuid = uuid,
                photoLink = "",
                name = "Бэргер",
                newPrice = 99,
                oldPrice = 100
            )
        FoodDeliveryTheme {
            Screen(
                state = ConsumerCartState.State(
                    consumerCartData = ConsumerCartData(
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
                            getMenuProductItem("7"),
                        )
                    ),
                    screenState = ConsumerCartState.ScreenState.SUCCESS
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
                state = ConsumerCartState.State(
                    consumerCartData = ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCartState.ScreenState.EMPTY
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
                state = ConsumerCartState.State(
                    consumerCartData = ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCartState.ScreenState.LOADING
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
                state = ConsumerCartState.State(
                    consumerCartData = ConsumerCartData(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                        firstOrderDiscount = "10",
                        recommendations = emptyList()
                    ),
                    screenState = ConsumerCartState.ScreenState.ERROR
                ),
                onAction = {}
            )
        }
    }
}
