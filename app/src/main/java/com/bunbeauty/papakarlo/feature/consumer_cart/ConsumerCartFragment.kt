package com.bunbeauty.papakarlo.feature.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.consumer_cart.model.CartProductItem
import com.bunbeauty.papakarlo.feature.consumer_cart.model.ConsumerCartUI
import com.bunbeauty.papakarlo.feature.consumer_cart.ui.CartProductItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumerCartFragment : BaseFragment(R.layout.layout_compose) {

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getConsumerCart()
        viewBinding.root.setContentWithTheme {
            val consumerCartState by viewModel.consumerCartState.collectAsStateWithLifecycle()
            ConsumerCartScreen(
                consumerCartState = consumerCartState,
                onMenuClicked = viewModel::onMenuClicked,
                onErrorButtonClicked = viewModel::getConsumerCart,
                addProductToCartClicked = viewModel::onAddCardProductClicked,
                removeProductFromCartClicked = viewModel::onRemoveCardProductClicked,
                onProductClicked = viewModel::onProductClicked,
                onCreateOrderClicked = viewModel::onCreateOrderClicked,
            )
        }
    }

    @Composable
    private fun ConsumerCartScreen(
        consumerCartState: State<ConsumerCartUI>,
        onMenuClicked: () -> Unit,
        onErrorButtonClicked: () -> Unit,
        addProductToCartClicked: (String) -> Unit,
        removeProductFromCartClicked: (String) -> Unit,
        onProductClicked: (CartProductItem) -> Unit,
        onCreateOrderClicked: () -> Unit,
    ) {
        FoodDeliveryScaffold(
            title = stringResource(id = R.string.title_cart),
            backActionClick = {
                findNavController().popBackStack()
            }
        ) {
            when (consumerCartState) {
                is State.Loading -> LoadingScreen()
                is State.Success -> ConsumerCartSuccessScreen(
                    consumerCart = consumerCartState.data,
                    addProductToCartClicked = addProductToCartClicked,
                    removeProductFromCartClicked = removeProductFromCartClicked,
                    onProductClicked = onProductClicked,
                    onCreateOrderClicked = onCreateOrderClicked,
                )
                is State.Empty -> {
                    EmptyScreen(
                        imageId = R.drawable.ic_cart_24,
                        imageDescriptionId = R.string.description_consumer_cart_empty,
                        mainTextId = R.string.title_consumer_cart_empty,
                        extraTextId = R.string.msg_consumer_cart_empty,
                        buttonTextId = R.string.action_consumer_cart_menu,
                        onClick = onMenuClicked
                    )
                }
                is State.Error -> {
                    ErrorScreen(
                        mainTextId = R.string.error_consumer_cart_loading,
                        onClick = onErrorButtonClicked
                    )
                }
            }
        }
    }

    @Composable
    private fun ConsumerCartSuccessScreen(
        consumerCart: ConsumerCartUI,
        addProductToCartClicked: (String) -> Unit,
        removeProductFromCartClicked: (String) -> Unit,
        onProductClicked: (CartProductItem) -> Unit,
        onCreateOrderClicked: () -> Unit,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.mainColors.background)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = FoodDeliveryTheme.dimensions.mediumSpace),
                            text = stringResource(R.string.msg_consumer_cart_free_delivery_from) + consumerCart.forFreeDelivery,
                            style = FoodDeliveryTheme.typography.bodyLarge,
                            color = FoodDeliveryTheme.colors.mainColors.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                    itemsIndexed(consumerCart.cartProductList) { i, cartProductItemModel ->
                        CartProductItem(
                            modifier = Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                            ),
                            cartProductItem = cartProductItemModel,
                            onCountIncreased = {
                                addProductToCartClicked(cartProductItemModel.menuProductUuid)
                            },
                            onCountDecreased = {
                                removeProductFromCartClicked(cartProductItemModel.menuProductUuid)
                            },
                            onClick = {
                                onProductClicked(cartProductItemModel)
                            }
                        )
                    }
                }
            }
            FoodDeliverySurface(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace)) {
                    Row {
                        Text(
                            text = stringResource(R.string.title_consumer_cart_total),
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        consumerCart.oldTotalCost?.let { oldTotalCost ->
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
                            text = consumerCart.newTotalCost,
                            style = FoodDeliveryTheme.typography.bodyMedium.bold,
                            color = FoodDeliveryTheme.colors.mainColors.onSurface
                        )
                    }
                    MainButton(
                        modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_consumer_cart_creeate_order,
                        onClick = onCreateOrderClicked
                    )
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartSuccessScreenPreview() {
        FoodDeliveryTheme {
            val cartProductItemModel =
                CartProductItem(
                    uuid = "",
                    name = "Бэргер",
                    newCost = "300 ₽",
                    oldCost = "330 ₽",
                    photoLink = "",
                    count = 3,
                    menuProductUuid = ""
                )
            ConsumerCartScreen(
                State.Success(
                    ConsumerCartUI(
                        forFreeDelivery = "500 ₽",
                        cartProductList = listOf(
                            cartProductItemModel,
                            cartProductItemModel,
                            cartProductItemModel,
                            cartProductItemModel,
                            cartProductItemModel,
                        ),
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽",
                    )
                ),
                onMenuClicked = {},
                onErrorButtonClicked = {},
                addProductToCartClicked = {},
                removeProductFromCartClicked = {},
                onProductClicked = {},
                onCreateOrderClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartEmptyScreenPreview() {
        FoodDeliveryTheme {
            ConsumerCartScreen(
                State.Empty(),
                onMenuClicked = {},
                onErrorButtonClicked = {},
                addProductToCartClicked = {},
                removeProductFromCartClicked = {},
                onProductClicked = {},
                onCreateOrderClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartLoadingScreenPreview() {
        FoodDeliveryTheme {
            ConsumerCartScreen(
                State.Loading(),
                onMenuClicked = {},
                onErrorButtonClicked = {},
                addProductToCartClicked = {},
                removeProductFromCartClicked = {},
                onProductClicked = {},
                onCreateOrderClicked = {},
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartErrorScreenPreview() {
        FoodDeliveryTheme {
            ConsumerCartScreen(
                State.Error("Не удалось загрузить корзину"), onMenuClicked = {},
                onErrorButtonClicked = {},
                addProductToCartClicked = {},
                removeProductFromCartClicked = {},
                onProductClicked = {},
                onCreateOrderClicked = {},
            )
        }
    }
}
