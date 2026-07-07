package com.bunbeauty.order.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.core.model.SuccessLoginDirection
import com.bunbeauty.core.motivation.Motivation
import com.bunbeauty.core.motivation.MotivationUi
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.theme.medium
import com.bunbeauty.designsystem.ui.LocalBottomBarPadding
import com.bunbeauty.designsystem.ui.SharedTransitionPreview
import com.bunbeauty.designsystem.ui.element.FoodDeliveryAction
import com.bunbeauty.designsystem.ui.element.FoodDeliveryProductItem
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.element.button.SecondaryButton
import com.bunbeauty.designsystem.ui.element.card.DiscountCard
import com.bunbeauty.designsystem.ui.element.card.FoodDeliveryItem
import com.bunbeauty.designsystem.ui.element.surface.FoodDeliverySurface
import com.bunbeauty.designsystem.ui.screen.EmptyScreen
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
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
import papakarlo.designsystem.generated.resources.action_consumer_delete
import papakarlo.designsystem.generated.resources.common_cancel
import papakarlo.designsystem.generated.resources.description_consumer_cart_empty
import papakarlo.designsystem.generated.resources.error_consumer_cart_add_product
import papakarlo.designsystem.generated.resources.error_consumer_cart_loading
import papakarlo.designsystem.generated.resources.error_consumer_cart_remove_product
import papakarlo.designsystem.generated.resources.ic_basket_24
import papakarlo.designsystem.generated.resources.ic_cart_24
import papakarlo.designsystem.generated.resources.msg_consumer_cart_empty
import papakarlo.designsystem.generated.resources.msg_consumer_cart_recommendations
import papakarlo.designsystem.generated.resources.title_cart
import papakarlo.designsystem.generated.resources.title_consumer_cart_discount
import papakarlo.designsystem.generated.resources.title_consumer_cart_empty
import papakarlo.designsystem.generated.resources.title_consumer_cart_total
import papakarlo.designsystem.generated.resources.title_consumer_delete_orders

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
    animatedContentScope: AnimatedContentScope,
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
    ConsumerCartScreen(
        viewState = viewState.toConsumerCartViewState(),
        onAction = onAction,
        animatedContentScope = animatedContentScope,
    )
}

@Composable
fun ConsumerCartScreen(
    viewState: ConsumerCartViewState,
    onAction: (ConsumerCart.Action) -> Unit,
    animatedContentScope: AnimatedVisibilityScope,
) {
    FoodDeliveryScaffold(
        title = stringResource(resource = Res.string.title_cart),
        backActionClick = {
            onAction(ConsumerCart.Action.BackClick)
        },
        topActions =
            when (viewState) {
                ConsumerCartViewState.Error -> persistentListOf()
                ConsumerCartViewState.Loading -> persistentListOf()
                is ConsumerCartViewState.Success ->
                    if (viewState.cartProductList.isNotEmpty()) {
                        persistentListOf(
                            FoodDeliveryAction(
                                iconId = Res.drawable.ic_basket_24,
                                onClick = {
                                    onAction(ConsumerCart.Action.OnClearConsumerCartClick)
                                },
                            ),
                        )
                    } else {
                        persistentListOf()
                    }
            },
        backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
    ) {
        when (viewState) {
            ConsumerCartViewState.Loading -> LoadingScreen()

            is ConsumerCartViewState.Success -> {
                ConsumerCartSuccessScreen(
                    viewState = viewState,
                    onAction = onAction,
                    animatedContentScope = animatedContentScope,
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
