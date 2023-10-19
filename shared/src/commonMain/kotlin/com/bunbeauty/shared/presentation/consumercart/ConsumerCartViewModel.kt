package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
) : SharedStateViewModel<ConsumerCartState.State, ConsumerCartState.Action, ConsumerCartState.Event>(
    ConsumerCartState.State(
        consumerCartData = ConsumerCartData(
            forFreeDelivery = "",
            cartProductList = listOf(),
            oldTotalCost = null,
            newTotalCost = "",
            firstOrderDiscount = null
        ),
        screenState = ConsumerCartState.ScreenState.LOADING,
    )
) {

    private var observeConsumerCartJob: Job? = null

    override fun handleAction(action: ConsumerCartState.Action) {

        when (action) {
            is ConsumerCartState.Action.AddProductToCartClick -> onAddCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )
            ConsumerCartState.Action.BackClick -> navigateBack()
            is ConsumerCartState.Action.ConsumeEvents -> consumeEvents(action.eventList)
            ConsumerCartState.Action.Init -> init()
            ConsumerCartState.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCartState.Action.OnErrorButtonClick -> init()
            ConsumerCartState.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCartState.Action.OnProductClick -> onProductClicked(cartProductItem = action.cartProductItem)
            is ConsumerCartState.Action.RemoveProductFromCartClick -> onRemoveCardProductClicked(
                menuProductUuid = action.menuProductUuid
            )
        }
    }


    private fun navigateBack() {
        event {
            ConsumerCartState.Event.NavigateBack
        }
    }

    private fun init() {
        state { oldState ->
            oldState.copy(
                screenState = ConsumerCartState.ScreenState.LOADING
            )
        }
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob =
            cartProductInteractor.observeConsumerCart().onEach { consumerCart ->
                Logger.logD("getConsumerCart", "getConsumerCart $consumerCart")
                state { dataState ->
                    if (consumerCart == null) {
                        dataState.copy(screenState = ConsumerCartState.ScreenState.ERROR)
                    } else {
                        dataState.copy(
                            screenState = getConsumerCartDataState(consumerCart),
                            consumerCartData = getConsumerCartData(
                                consumerCart = consumerCart
                            )
                        )
                    }
                }
            }.launchIn(sharedScope)
    }

    private fun onMenuClicked() {
        event {
            ConsumerCartState.Event.NavigateToMenu
        }
    }

    private fun onCreateOrderClicked() {
        sharedScope.launchSafe(
            block = {
                event {
                    if (userInteractor.isUserAuthorize()) {
                        ConsumerCartState.Event.NavigateToCreateOrder
                    } else {
                        ConsumerCartState.Event.NavigateToLogin
                    }
                }
            },
            onError = {
                // TODO handle error
            }
        )
    }

    private fun onProductClicked(cartProductItem: CartProductItem) {
        event {
            ConsumerCartState.Event.NavigateToProduct(cartProductItem)
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

    private fun getConsumerCartData(
        consumerCart: ConsumerCart,
    ): ConsumerCartData? {
        return when (consumerCart) {
            is ConsumerCart.Empty -> null
            is ConsumerCart.WithProducts -> ConsumerCartData(
                forFreeDelivery = "${consumerCart.forFreeDelivery} $RUBLE_CURRENCY",
                cartProductList = consumerCart.cartProductList.map(::toItem),
                oldTotalCost = consumerCart.oldTotalCost?.let { oldTotalCost ->
                    oldTotalCost.toString() + RUBLE_CURRENCY
                },
                newTotalCost = consumerCart.newTotalCost.toString() + RUBLE_CURRENCY,
                firstOrderDiscount = consumerCart.discount?.let { discount ->
                    discount.toString() + PERCENT
                }
            )
        }
    }

    private fun getConsumerCartDataState(consumerCart: ConsumerCart): ConsumerCartState.ScreenState {
        return when (consumerCart) {
            is ConsumerCart.Empty -> ConsumerCartState.ScreenState.EMPTY
            is ConsumerCart.WithProducts -> ConsumerCartState.ScreenState.SUCCESS
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
