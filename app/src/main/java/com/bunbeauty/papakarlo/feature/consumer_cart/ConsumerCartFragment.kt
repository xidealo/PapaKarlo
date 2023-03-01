package com.bunbeauty.papakarlo.feature.consumer_cart

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.element.BlurLine
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.EmptyScreen
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentConsumerCartBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.consumer_cart.model.ConsumerCartUI
import com.bunbeauty.papakarlo.feature.consumer_cart.ui.CartProductItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumerCartFragment : BaseFragment(R.layout.fragment_consumer_cart) {

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentConsumerCartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getConsumerCart()
        viewBinding.fragmentConsumerCartCvMain.setContentWithTheme {
            val consumerCartState by viewModel.consumerCartState.collectAsState()
            ConsumerCartScreen(consumerCartState)
        }
    }

    @Composable
    private fun ConsumerCartScreen(consumerCartState: State<ConsumerCartUI>) {
        when (consumerCartState) {
            is State.Loading -> LoadingScreen()
            is State.Success -> ConsumerCartSuccessScreen(consumerCartState.data)
            is State.Empty -> {
                EmptyScreen(
                    imageId = R.drawable.empty_cart,
                    imageDescriptionId = R.string.description_consumer_cart_empty,
                    textId = R.string.msg_consumer_cart_empty,
                    buttonTextId = R.string.action_consumer_cart_menu,
                    onClick = viewModel::onMenuClicked
                )
            }
            is State.Error -> {
                ErrorScreen(mainTextId = R.string.error_consumer_cart_loading) {
                    viewModel.getConsumerCart()
                }
            }
        }
    }

    @Composable
    private fun ConsumerCartSuccessScreen(consumerCart: ConsumerCartUI) {
        Column(modifier = Modifier.fillMaxSize()) {
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
                            style = FoodDeliveryTheme.typography.body1,
                            color = FoodDeliveryTheme.colors.onBackground,
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
                                viewModel.addProductToCart(cartProductItemModel.menuProductUuid)
                            },
                            onCountDecreased = {
                                viewModel.removeProductFromCart(cartProductItemModel.menuProductUuid)
                            }
                        ) {
                            viewModel.onProductClicked(cartProductItemModel)
                        }
                    }
                }
                BlurLine(modifier = Modifier.align(Alignment.BottomCenter))
            }
            Column(
                modifier = Modifier
                    .background(FoodDeliveryTheme.colors.surface)
                    .padding(FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.title_consumer_cart_total),
                        style = FoodDeliveryTheme.typography.h2,
                        color = FoodDeliveryTheme.colors.onSurface
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        consumerCart.oldTotalCost?.let { oldTotalCost ->
                            Text(
                                modifier = Modifier
                                    .padding(end = FoodDeliveryTheme.dimensions.smallSpace),
                                text = oldTotalCost,
                                style = FoodDeliveryTheme.typography.h2,
                                color = FoodDeliveryTheme.colors.onSurfaceVariant,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = consumerCart.newTotalCost,
                            style = FoodDeliveryTheme.typography.h2,
                            color = FoodDeliveryTheme.colors.onSurface
                        )
                    }
                }
                MainButton(
                    modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_consumer_cart_creeate_order
                ) {
                    viewModel.onCreateOrderClicked()
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartSuccessScreenPreview() {
        val cartProductItemModel =
            com.bunbeauty.papakarlo.feature.consumer_cart.model.CartProductItem(
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
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartEmptyScreenPreview() {
        ConsumerCartScreen(State.Empty())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartLoadingScreenPreview() {
        ConsumerCartScreen(State.Loading())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartErrorScreenPreview() {
        ConsumerCartScreen(State.Error("Не удалось загрузить корзину"))
    }
}
