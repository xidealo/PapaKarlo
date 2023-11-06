package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.CartAddEvent
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.RecommendationAddEvent
import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.menu.MenuProductItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val analyticService: AnalyticService,
) : SharedStateViewModel<ConsumerCart.State, ConsumerCart.Action, ConsumerCart.Event>(
    ConsumerCart.State(
        consumerCartData = ConsumerCartData(
            forFreeDelivery = "",
            cartProductList = listOf(),
            oldTotalCost = null,
            newTotalCost = "",
            firstOrderDiscount = null,
            recommendations = emptyList()
        ),
        screenState = ConsumerCart.ScreenState.LOADING,
    )
) {

    private var observeConsumerCartJob: Job? = null

    override fun handleAction(action: ConsumerCart.Action) {

        when (action) {
            is ConsumerCart.Action.AddProductToCartClick -> addCartProductToCartClick(
                menuProductUuid = action.menuProductUuid
            )

            ConsumerCart.Action.BackClick -> navigateBack()
            ConsumerCart.Action.Init -> init()
            ConsumerCart.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCart.Action.OnErrorButtonClick -> init()
            ConsumerCart.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCart.Action.OnProductClick -> onProductClicked(
                uuid = action.cartProductItem.menuProductUuid,
                name = action.cartProductItem.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT
            )

            is ConsumerCart.Action.RemoveProductFromCartClick -> onRemoveCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )

            is ConsumerCart.Action.AddRecommendationProductToCartClick -> addRecommendationProductClicked(
                menuProductUuid = action.menuProductUuid
            )

            is ConsumerCart.Action.RecommendationClick -> onProductClicked(
                uuid = action.menuProductUuid,
                name = action.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT
            )
        }
    }

    private fun navigateBack() {
        event {
            ConsumerCart.Event.NavigateBack
        }
    }

    private fun init() {
        state { oldState ->
            oldState.copy(
                screenState = ConsumerCart.ScreenState.LOADING
            )
        }
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob =
            cartProductInteractor.observeConsumerCart().onEach { consumerCart ->
                Logger.logD("getConsumerCart", "getConsumerCart $consumerCart")
                state { dataState ->
                    if (consumerCart == null) {
                        dataState.copy(screenState = ConsumerCart.ScreenState.ERROR)
                    } else {
                        dataState.copy(
                            screenState = getConsumerCartDataState(consumerCart),
                            consumerCartData = getConsumerCartData(
                                consumerCartDomain = consumerCart
                            ),
                        )
                    }
                }
            }.launchIn(sharedScope)
    }

    private fun onMenuClicked() {
        event {
            ConsumerCart.Event.NavigateToMenu
        }
    }

    private fun onCreateOrderClicked() {
        sharedScope.launchSafe(
            block = {
                event {
                    if (userInteractor.isUserAuthorize()) {
                        ConsumerCart.Event.NavigateToCreateOrder
                    } else {
                        ConsumerCart.Event.NavigateToLogin
                    }
                }
            },
            onError = {
                // TODO handle error
            }
        )
    }

    private fun onProductClicked(
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        event {
            ConsumerCart.Event.NavigateToProduct(
                uuid = uuid,
                name = name,
                productDetailsOpenedFrom = productDetailsOpenedFrom
            )
        }
    }

    private fun addRecommendationProductClicked(menuProductUuid: String) {
        analyticService.sendEvent(
            RecommendationAddEvent,
            params = listOf(EventParameter("menuProductUuid", menuProductUuid)),
        )

        addProduct(
            menuProductUuid = menuProductUuid
        )
    }

    private fun addCartProductToCartClick(menuProductUuid: String) {
        addProduct(
            menuProductUuid = menuProductUuid
        )
        analyticService.sendEvent(CartAddEvent)
    }

    private fun addProduct(menuProductUuid: String) {
        sharedScope.launchSafe(
            block = {
                addCartProductUseCase(menuProductUuid)
            },
            onError = {
                // TODO handle error
            }
        )
    }

    private fun onRemoveCardProductClicked(menuProductUuid: String) {
        sharedScope.launchSafe(
            block = {
                removeCartProductUseCase(menuProductUuid)
            },
            onError = {
                // TODO handle error
            }
        )
    }

    private suspend fun getConsumerCartData(
        consumerCartDomain: ConsumerCartDomain,
    ): ConsumerCartData? {
        return when (consumerCartDomain) {
            is ConsumerCartDomain.Empty -> null
            is ConsumerCartDomain.WithProducts -> ConsumerCartData(
                forFreeDelivery = "${consumerCartDomain.forFreeDelivery} $RUBLE_CURRENCY",
                cartProductList = consumerCartDomain.cartProductList.map(::toItem),
                oldTotalCost = consumerCartDomain.oldTotalCost?.let { oldTotalCost ->
                    oldTotalCost.toString() + RUBLE_CURRENCY
                },
                newTotalCost = consumerCartDomain.newTotalCost.toString() + RUBLE_CURRENCY,
                firstOrderDiscount = consumerCartDomain.discount?.let { discount ->
                    discount.toString() + PERCENT
                },
                recommendations = getRecommendationsUseCase().map { recommendationProduct ->
                    with(recommendationProduct.menuProduct) {
                        MenuProductItem(
                            uuid = uuid,
                            photoLink = photoLink,
                            name = name,
                            oldPrice = oldPrice,
                            newPrice = newPrice
                        )
                    }
                }
            )
        }
    }

    private fun getConsumerCartDataState(consumerCartDomain: ConsumerCartDomain): ConsumerCart.ScreenState {
        return when (consumerCartDomain) {
            is ConsumerCartDomain.Empty -> ConsumerCart.ScreenState.EMPTY
            is ConsumerCartDomain.WithProducts -> ConsumerCart.ScreenState.SUCCESS
        }
    }

    private fun toItem(lightCartProduct: LightCartProduct): CartProductItem {
        return CartProductItem(
            uuid = lightCartProduct.uuid,
            name = lightCartProduct.name,
            newCost = lightCartProduct.newCost.toString() + RUBLE_CURRENCY,
            oldCost = lightCartProduct.oldCost?.let { oldCost -> oldCost.toString() + RUBLE_CURRENCY },
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid
        )
    }

}
