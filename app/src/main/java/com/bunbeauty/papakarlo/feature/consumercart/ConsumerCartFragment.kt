package com.bunbeauty.papakarlo.feature.consumercart

import android.os.Bundle
import android.view.View
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
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
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryItem
import com.bunbeauty.papakarlo.common.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.papakarlo.common.ui.icon24
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
import com.bunbeauty.papakarlo.feature.consumercart.mapper.toConsumerCartViewState
import com.bunbeauty.papakarlo.feature.consumercart.state.ConsumerCartViewState
import com.bunbeauty.papakarlo.feature.consumercart.ui.CartProductItem
import com.bunbeauty.papakarlo.feature.main.IMessageHost
import com.bunbeauty.papakarlo.feature.menu.state.MenuItemUi
import com.bunbeauty.papakarlo.feature.menu.ui.MenuProductItem
import com.bunbeauty.shared.domain.model.SuccessLoginDirection
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart
import com.bunbeauty.shared.presentation.consumercart.ConsumerCartViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumerCartFragment :
    BaseComposeFragment<ConsumerCart.DataState, ConsumerCartViewState, ConsumerCart.Action, ConsumerCart.Event>() {

    override val viewModel: ConsumerCartViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onAction(ConsumerCart.Action.Init)
    }

    @Composable
    override fun ConsumerCart.DataState.mapState(): ConsumerCartViewState {
        return toConsumerCartViewState()
    }

    @Composable
    override fun Screen(
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
            ConsumerCart.Event.ShowAddProductError -> (activity as? IMessageHost)?.showErrorMessage(
                resources.getString(R.string.error_consumer_cart_add_product)
            )

            ConsumerCart.Event.ShowRemoveProductError -> (activity as? IMessageHost)?.showErrorMessage(
                resources.getString(R.string.error_consumer_cart_remove_product)
            )
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
                        enabled = bottomPanelInfo.isOrderCreationAvailable,
                        onClick = {
                            onAction(ConsumerCart.Action.OnCreateOrderClick)
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun Motivation(
        motivation: ConsumerCartViewState.MotivationUi?,
        modifier: Modifier = Modifier
    ) {
        motivation ?: return

        Column(
            modifier = modifier,
            verticalArrangement = spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = spacedBy(8.dp)
            ) {
                val iconId = when (motivation) {
                    is ConsumerCartViewState.MotivationUi.MinOrderCost -> R.drawable.ic_warning
                    is ConsumerCartViewState.MotivationUi.ForLowerDelivery,
                    is ConsumerCartViewState.MotivationUi.LowerDeliveryAchieved -> R.drawable.ic_delivery
                }
                val iconTint = when (motivation) {
                    is ConsumerCartViewState.MotivationUi.MinOrderCost -> {
                        FoodDeliveryTheme.colors.statusColors.warning
                    }

                    is ConsumerCartViewState.MotivationUi.ForLowerDelivery,
                    is ConsumerCartViewState.MotivationUi.LowerDeliveryAchieved -> {
                        FoodDeliveryTheme.colors.mainColors.onSurfaceVariant
                    }
                }
                Icon(
                    modifier = Modifier.icon24(),
                    painter = painterResource(iconId),
                    tint = iconTint,
                    contentDescription = null
                )

                val motivationText = when (motivation) {
                    is ConsumerCartViewState.MotivationUi.MinOrderCost -> {
                        buildAnnotatedStringWithBold(
                            text = stringResource(R.string.msg_consumer_cart_min_order, motivation.cost),
                            subtextToSelect = motivation.cost
                        )
                    }

                    is ConsumerCartViewState.MotivationUi.ForLowerDelivery -> {
                        val textId = if (motivation.isFree) {
                            R.string.msg_consumer_cart_for_free_delivery
                        } else {
                            R.string.msg_consumer_cart_for_lower_delivery
                        }
                        buildAnnotatedStringWithBold(
                            text = stringResource(textId, motivation.increaseAmountBy),
                            subtextToSelect = motivation.increaseAmountBy
                        )
                    }

                    is ConsumerCartViewState.MotivationUi.LowerDeliveryAchieved -> {
                        val textId = if (motivation.isFree) {
                            R.string.msg_consumer_cart_free_delivery
                        } else {
                            R.string.msg_consumer_cart_lower_delivery
                        }
                        buildAnnotatedString {
                            append(stringResource(textId))
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .animateContentSize(tween(500))
                        .weight(1f),
                    text = motivationText,
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.mainColors.onSurface
                )
            }

            when (motivation) {
                is ConsumerCartViewState.MotivationUi.MinOrderCost -> null
                is ConsumerCartViewState.MotivationUi.ForLowerDelivery -> motivation.progress
                is ConsumerCartViewState.MotivationUi.LowerDeliveryAchieved -> 1f
            }?.let { progress ->
                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(500),
                    label = "progress"
                )
                val animatedColor by animateColorAsState(
                    targetValue = lerp(
                        FoodDeliveryTheme.colors.statusColors.warning,
                        FoodDeliveryTheme.colors.statusColors.positive,
                        progress
                    ),
                    animationSpec = tween(500),
                    label = "color"
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    progress = animatedProgress,
                    color = animatedColor,
                    trackColor = FoodDeliveryTheme.colors.mainColors.disabled,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }

    @Composable
    private fun buildAnnotatedStringWithBold(
        text: String,
        subtextToSelect: String
    ): AnnotatedString {
        return buildAnnotatedString {
            val startIndex = text.indexOf(subtextToSelect)
            val endIndex = startIndex + subtextToSelect.length
            append(text)
            addStyle(
                style = FoodDeliveryTheme.typography.bodyMedium.bold.toSpanStyle(),
                start = startIndex,
                end = endIndex
            )
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
            Screen(
                viewState = ConsumerCartViewState.Success(
                    cartProductList = persistentListOf(
                        getCartProductItemModel("1"),
                        getCartProductItemModel("2"),
                        getCartProductItemModel("3"),
                        getCartProductItemModel("4"),
                        getCartProductItemModel("5")
                    ),
                    bottomPanelInfo = ConsumerCartViewState.BottomPanelInfoUi(
                        motivation = ConsumerCartViewState.MotivationUi.ForLowerDelivery(
                            increaseAmountBy = "550 ₽",
                            progress = 0.5f,
                            isFree = true
                        ),
                        discount = "10%",
                        oldTotalCost = "1650 ₽",
                        newTotalCost = "1500 ₽"
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
            Screen(
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
            Screen(
                viewState = ConsumerCartViewState.Loading,
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ConsumerCartErrorScreenPreview() {
        FoodDeliveryTheme {
            Screen(
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
}
