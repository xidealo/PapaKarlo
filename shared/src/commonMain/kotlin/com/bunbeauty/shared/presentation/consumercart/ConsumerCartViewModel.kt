package com.bunbeauty.shared.presentation.consumercart

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
) : SharedStateViewModel<ConsumerCart.ViewDataState, ConsumerCart.Action, ConsumerCart.Event>(
    ConsumerCart.ViewDataState(
        consumerCartData = ConsumerCart.ViewDataState.ConsumerCartData(
            forFreeDelivery = "",
            cartProductList = listOf(),
            oldTotalCost = null,
            newTotalCost = "",
            firstOrderDiscount = null,
            recommendations = emptyList()
        ),
        screenState = ConsumerCart.ViewDataState.ScreenState.LOADING,
    )
) {

    private var observeConsumerCartJob: Job? = null

    override fun reduce(action: ConsumerCart.Action, dataState: ConsumerCart.ViewDataState) {
        when (action) {
            is ConsumerCart.Action.AddProductToCartClick -> onAddCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )

            ConsumerCart.Action.BackClick -> navigateBack()
            ConsumerCart.Action.Init -> observeConsumerCart()
            ConsumerCart.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCart.Action.OnErrorButtonClick -> observeConsumerCart()
            ConsumerCart.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCart.Action.OnProductClick -> onProductClicked(
                uuid = action.cartProductItem.menuProductUuid,
                name = action.cartProductItem.name
            )

            is ConsumerCart.Action.RemoveProductFromCartClick -> onRemoveCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )

            is ConsumerCart.Action.AddProductToRecommendationClick -> onAddCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )

            is ConsumerCart.Action.RecommendationClick -> onProductClicked(
                uuid = action.menuProductUuid,
                name = action.name
            )
        }
    }

    private fun navigateBack() {
        addEvent {
            ConsumerCart.Event.NavigateBack
        }
    }

    private fun observeConsumerCart() {
        setState {
            copy(screenState = ConsumerCart.ViewDataState.ScreenState.LOADING)
        }
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob = cartProductInteractor.observeConsumerCart().onEach { consumerCartDomain ->
            Logger.logD("getConsumerCart", "getConsumerCart $consumerCartDomain")
            setState {
                if (consumerCartDomain == null) {
                    copy(screenState = ConsumerCart.ViewDataState.ScreenState.ERROR)
                } else {
                    copy(
                        screenState = getConsumerCartDataState(consumerCartDomain),
                        consumerCartData = getConsumerCartData(
                            consumerCartDomain = consumerCartDomain
                        ),
                    )
                }
            }
        }.launchIn(sharedScope)
    }

    private fun onMenuClicked() {
        addEvent {
            ConsumerCart.Event.NavigateToMenu
        }
    }

    private fun onCreateOrderClicked() {
        sharedScope.launchSafe(
            block = {
                addEvent {
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

    private fun onProductClicked(uuid: String, name: String) {
        addEvent {
            ConsumerCart.Event.NavigateToProduct(uuid = uuid, name = name)
        }
    }

    private fun onAddCardProductClicked(menuProductUuid: String) {
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
    ): ConsumerCart.ViewDataState.ConsumerCartData? {
        return when (consumerCartDomain) {
            is ConsumerCartDomain.Empty -> null
            is ConsumerCartDomain.WithProducts -> ConsumerCart.ViewDataState.ConsumerCartData(
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

    private fun getConsumerCartDataState(consumerCartDomain: ConsumerCartDomain): ConsumerCart.ViewDataState.ScreenState {
        return when (consumerCartDomain) {
            is ConsumerCartDomain.Empty -> ConsumerCart.ViewDataState.ScreenState.EMPTY
            is ConsumerCartDomain.WithProducts -> ConsumerCart.ViewDataState.ScreenState.SUCCESS
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
