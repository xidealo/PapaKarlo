package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.DecreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.IncreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.RemoveCartProductClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Constants.PERCENT
import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.domain.cart.GetRecommendationsUseCase
import com.bunbeauty.core.domain.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.core.domain.cart.RemoveCartProductUseCase
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
import com.bunbeauty.core.domain.cart.ICartProductInteractor
import com.bunbeauty.core.domain.user.IUserInteractor
import com.bunbeauty.core.model.product.MenuProduct
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.MenuItem
import com.bunbeauty.core.model.cart.ConsumerCartDomain
import com.bunbeauty.core.model.mapper.toMenuProductItem
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.motivation.GetMotivationUseCase
import com.bunbeauty.core.domain.motivation.Motivation
import com.bunbeauty.core.domain.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.presentation.consumercart.mapper.toCartProductItem
import com.bunbeauty.core.motivation.toMotivationData
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val CONSUMER_CART_VIEW_MODEL_TAG = "ConsumerCartViewModel"

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val increaseCartProductCountUseCase: IncreaseCartProductCountUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getMotivationUseCase: GetMotivationUseCase,
    private val analyticService: AnalyticService,
    private val isOrderAvailableUseCase: IsOrderAvailableUseCase,
) : SharedStateViewModel<ConsumerCart.DataState, ConsumerCart.Action, ConsumerCart.Event>(
        ConsumerCart.DataState(
            state = ConsumerCart.DataState.State.LOADING,
            motivation = null,
            cartProductItemList = emptyList(),
            recommendationList = emptyList(),
            discount = null,
            oldTotalCost = null,
            newTotalCost = "",
            orderAvailable = false,
        ),
    ) {
    private var observeConsumerCartJob: Job? = null

    override fun reduce(
        action: ConsumerCart.Action,
        dataState: ConsumerCart.DataState,
    ) {
        when (action) {
            is ConsumerCart.Action.AddProductToCartClick ->
                increaseCartProductToCartClick(
                    cartProductUuid = action.cartProductUuid,
                )

            ConsumerCart.Action.BackClick -> navigateBack()
            ConsumerCart.Action.Init -> {
                observeConsumerCart()
                checkOrderAvailable()
            }

            ConsumerCart.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCart.Action.OnErrorButtonClick -> observeConsumerCart()
            ConsumerCart.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCart.Action.OnCartProductClick ->
                onCartProductClicked(
                    cartProductUuid = action.cartProductUuid,
                )

            is ConsumerCart.Action.RemoveProductFromCartClick ->
                onRemoveCardProductClicked(
                    cartProductUuid = action.cartProductUuid,
                )

            is ConsumerCart.Action.AddRecommendationProductToCartClick ->
                addRecommendationProductClicked(
                    menuProductUuid = action.menuProductUuid,
                )

            is ConsumerCart.Action.RecommendationClick ->
                onRecommendationClicked(
                    recommendationUuid = action.menuProductUuid,
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
            copy(state = ConsumerCart.DataState.State.LOADING)
        }
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob =
            cartProductInteractor
                .observeConsumerCart()
                .onEach { consumerCart ->
                    val motivation =
                        if (consumerCart is ConsumerCartDomain.WithProducts) {
                            getMotivationUseCase(
                                newTotalCost = consumerCart.newTotalCost,
                                isDelivery = true,
                            )
                        } else {
                            null
                        }
                    val menuProductList = getRecommendationsUseCase()
                    setState {
                        copyWith(
                            consumerCart = consumerCart,
                            motivation = motivation,
                            recommendationList = menuProductList,
                        )
                    }
                }.launchIn(sharedScope)
    }

    private fun ConsumerCart.DataState.copyWith(
        consumerCart: ConsumerCartDomain?,
        motivation: Motivation?,
        recommendationList: List<MenuProduct>,
    ): ConsumerCart.DataState =
        if (consumerCart is ConsumerCartDomain.WithProducts) {
            copy(
                state = ConsumerCart.DataState.State.SUCCESS,
                motivation = motivation?.toMotivationData(),
                cartProductItemList =
                    consumerCart.cartProductList.map { lightCartProduct ->
                        lightCartProduct.toCartProductItem()
                    },
                discount =
                    consumerCart.discount?.let { discount ->
                        "$discount$PERCENT"
                    },
                oldTotalCost =
                    consumerCart.oldTotalCost?.let { oldTotalCost ->
                        "$oldTotalCost $RUBLE_CURRENCY"
                    },
                newTotalCost = "${consumerCart.newTotalCost} $RUBLE_CURRENCY",
                recommendationList =
                    recommendationList.map { menuProduct ->
                        menuProduct.toMenuProductItem()
                    },
            )
        } else {
            copy(
                state =
                    if (consumerCart == null) {
                        ConsumerCart.DataState.State.ERROR
                    } else {
                        ConsumerCart.DataState.State.SUCCESS
                    },
                cartProductItemList = emptyList(),
                oldTotalCost = null,
                newTotalCost = "",
                discount = null,
                recommendationList =
                    recommendationList.map { menuProduct ->
                        menuProduct.toMenuProductItem()
                    },
            )
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
                // Do nothing
            },
        )
    }

    private fun onCartProductClicked(cartProductUuid: String) {
        val cartProduct = findCartProduct(cartProductUuid = cartProductUuid) ?: return

        addEvent {
            ConsumerCart.Event.NavigateToProduct(
                uuid = cartProduct.menuProductUuid,
                name = cartProduct.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
                additionUuidList = cartProduct.additionUuidList,
                cartProductUuid = cartProduct.uuid,
            )
        }
    }

    private fun onRecommendationClicked(recommendationUuid: String) {
        val recommendation = findRecommendation(recommendationUuid = recommendationUuid) ?: return

        addEvent {
            ConsumerCart.Event.NavigateToProduct(
                uuid = recommendation.uuid,
                name = recommendation.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT,
                additionUuidList = emptyList(),
                cartProductUuid = null,
            )
        }
    }

    private fun addRecommendationProductClicked(menuProductUuid: String) {
        val menuProduct =
            dataState.value.recommendationList.find { menuProduct ->
                menuProduct.uuid == menuProductUuid
            } ?: return

        analyticService.sendEvent(
            event =
                AddRecommendationProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                ),
        )

        sharedScope.launchSafe(
            block = {
                if (menuProduct.hasAdditions) {
                    addEvent {
                        ConsumerCart.Event.NavigateToProduct(
                            uuid = menuProduct.uuid,
                            name = menuProduct.name,
                            productDetailsOpenedFrom = ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT,
                            additionUuidList = emptyList(),
                            cartProductUuid = null,
                        )
                    }
                } else {
                    addMenuProductUseCase(menuProductUuid = menuProductUuid)
                }
            },
            onError = {
                addEvent {
                    ConsumerCart.Event.ShowAddProductError
                }
            },
        )
    }

    private fun increaseCartProductToCartClick(cartProductUuid: String) {
        val cartProduct = findCartProduct(cartProductUuid = cartProductUuid) ?: return

        analyticService.sendEvent(
            event =
                IncreaseCartProductClickEvent(
                    menuProductUuidEventParameter =
                        MenuProductUuidEventParameter(
                            value = cartProduct.menuProductUuid,
                        ),
                ),
        )
        sharedScope.launchSafe(
            block = {
                increaseCartProductCountUseCase(cartProductUuid = cartProduct.uuid)
            },
            onError = {
                addEvent {
                    ConsumerCart.Event.ShowAddProductError
                }
            },
        )
    }

    private fun onRemoveCardProductClicked(cartProductUuid: String) {
        val cartProduct = findCartProduct(cartProductUuid = cartProductUuid) ?: return

        handleRemoveAnalytic(menuProductUuid = cartProduct.menuProductUuid)
        sharedScope.launchSafe(
            block = {
                removeCartProductUseCase(cartProductUuid = cartProduct.uuid)
            },
            onError = {
                addEvent {
                    ConsumerCart.Event.ShowRemoveProductError
                }
            },
        )
    }

    private fun handleRemoveAnalytic(menuProductUuid: String) {
        analyticService.sendEvent(
            event =
                DecreaseCartProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                ),
        )

        val isLast =
            dataState.value.cartProductItemList
                .find { cartProductItem ->
                    cartProductItem.menuProductUuid == menuProductUuid
                }?.count == 1
        if (isLast) {
            analyticService.sendEvent(
                event =
                    RemoveCartProductClickEvent(
                        menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                    ),
            )
        }
    }

    private fun findCartProduct(cartProductUuid: String): CartProductItem? =
        mutableDataState.value.cartProductItemList.find { cartProductItem ->
            cartProductItem.uuid == cartProductUuid
        }

    private fun findRecommendation(recommendationUuid: String): MenuItem.Product? =
        mutableDataState.value.recommendationList.find { menuProductItem ->
            menuProductItem.uuid == recommendationUuid
        }

    private fun checkOrderAvailable() {
        sharedScope.launchSafe(
            block = {
                setState {
                    copy(orderAvailable = isOrderAvailableUseCase())
                }
            },
            onError = { error ->
                Logger.logE(CONSUMER_CART_VIEW_MODEL_TAG, error.stackTraceToString())
            },
        )
    }
}
