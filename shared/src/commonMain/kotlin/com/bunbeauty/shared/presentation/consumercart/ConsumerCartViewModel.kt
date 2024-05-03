package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.DecreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.IncreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.RemoveCartProductClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.cart.GetConsumerCartWarningUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.feature.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.model.Motivation
import com.bunbeauty.shared.domain.feature.menu.AddMenuProductUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import com.bunbeauty.shared.presentation.consumercart.mapper.toCartProductItem
import com.bunbeauty.shared.presentation.consumercart.mapper.toWarningItem
import com.bunbeauty.shared.presentation.menu.mapper.toMenuProductItem
import com.bunbeauty.shared.presentation.menu.model.MenuItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsOpenedFrom
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConsumerCartViewModel(
    private val userInteractor: IUserInteractor,
    private val cartProductInteractor: ICartProductInteractor,
    private val increaseCartProductCountUseCase: IncreaseCartProductCountUseCase,
    private val addMenuProductUseCase: AddMenuProductUseCase,
    private val removeCartProductUseCase: RemoveCartProductUseCase,
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val getConsumerCartWarningUseCase: GetConsumerCartWarningUseCase,
    private val analyticService: AnalyticService,
) : SharedStateViewModel<ConsumerCart.DataState, ConsumerCart.Action, ConsumerCart.Event>(
    ConsumerCart.DataState(
        state = ConsumerCart.DataState.State.LOADING,
        motivation = null,
        cartProductItemList = emptyList(),
        recommendationList = emptyList(),
        discount = null,
        oldTotalCost = null,
        newTotalCost = "",
    )
) {

    private var observeConsumerCartJob: Job? = null

    override fun reduce(action: ConsumerCart.Action, dataState: ConsumerCart.DataState) {
        when (action) {
            is ConsumerCart.Action.AddProductToCartClick -> increaseCartProductToCartClick(
                cartProductUuid = action.cartProductUuid
            )

            ConsumerCart.Action.BackClick -> navigateBack()
            ConsumerCart.Action.Init -> observeConsumerCart()
            ConsumerCart.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCart.Action.OnErrorButtonClick -> observeConsumerCart()
            ConsumerCart.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCart.Action.OnCartProductClick -> onCartProductClicked(
                cartProductUuid = action.cartProductUuid
            )

            is ConsumerCart.Action.RemoveProductFromCartClick -> onRemoveCardProductClicked(
                cartProductUuid = action.cartProductUuid,
            )

            is ConsumerCart.Action.AddRecommendationProductToCartClick -> addRecommendationProductClicked(
                menuProductUuid = action.menuProductUuid,
            )

            is ConsumerCart.Action.RecommendationClick -> onRecommendationClicked(
                recommendationUuid = action.menuProductUuid
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
        observeConsumerCartJob = cartProductInteractor.observeConsumerCart()
            .onEach { consumerCart ->
                val warning = if (consumerCart is ConsumerCartDomain.WithProducts) {
                    getConsumerCartWarningUseCase(consumerCart)

                } else {
                    null
                }
                val menuProductList = getRecommendationsUseCase()
                setState {
                    copyWith(
                        consumerCart = consumerCart,
                        motivation = warning,
                        recommendationList = menuProductList
                    )
                }
            }.launchIn(sharedScope)
    }

    private fun ConsumerCart.DataState.copyWith(
        consumerCart: ConsumerCartDomain?,
        motivation: Motivation?,
        recommendationList: List<MenuProduct>
    ): ConsumerCart.DataState {
        return if (consumerCart is ConsumerCartDomain.WithProducts) {
            copy(
                state = ConsumerCart.DataState.State.SUCCESS,
                motivation = motivation?.toWarningItem(),
                cartProductItemList = consumerCart.cartProductList.mapIndexed { index, lightCartProduct ->
                    lightCartProduct.toCartProductItem()
                },
                discount = consumerCart.discount?.let { discount ->
                    "$discount$PERCENT"
                },
                oldTotalCost = consumerCart.oldTotalCost?.let { oldTotalCost ->
                    "$oldTotalCost $RUBLE_CURRENCY"
                },
                newTotalCost = "${consumerCart.newTotalCost} $RUBLE_CURRENCY",
                recommendationList = recommendationList.map { menuProduct ->
                    menuProduct.toMenuProductItem()
                }
            )
        } else {
            copy(
                state = if (consumerCart == null) {
                    ConsumerCart.DataState.State.ERROR
                } else {
                    ConsumerCart.DataState.State.EMPTY
                },
                cartProductItemList = emptyList(),
                oldTotalCost = null,
                newTotalCost = "",
                discount = null,
                recommendationList = recommendationList.map { menuProduct ->
                    menuProduct.toMenuProductItem()
                }
            )
        }
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
            }
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
        val menuProduct = dataState.value.recommendationList.find { menuProduct ->
            menuProduct.uuid == menuProductUuid
        } ?: return

        analyticService.sendEvent(
            event = AddRecommendationProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
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
            }
        )
    }

    private fun increaseCartProductToCartClick(cartProductUuid: String) {
        val cartProduct = findCartProduct(cartProductUuid = cartProductUuid) ?: return

        analyticService.sendEvent(
            event = IncreaseCartProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(
                    value = cartProduct.menuProductUuid
                )
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
            }
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
            }
        )
    }

    private fun handleRemoveAnalytic(menuProductUuid: String) {
        analyticService.sendEvent(
            event = DecreaseCartProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
            ),
        )

        val isLast = dataState.value.cartProductItemList.find { cartProductItem ->
            cartProductItem.menuProductUuid == menuProductUuid
        }?.count == 1
        if (isLast) {
            analyticService.sendEvent(
                event = RemoveCartProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
                ),
            )
        }
    }

    private fun findCartProduct(cartProductUuid: String): CartProductItem? {
        return mutableDataState.value.cartProductItemList.find { cartProductItem ->
            cartProductItem.uuid == cartProductUuid
        }
    }

    private fun findRecommendation(recommendationUuid: String): MenuItem.Product? {
        return mutableDataState.value.recommendationList.find { menuProductItem ->
            menuProductItem.uuid == recommendationUuid
        }
    }

}
