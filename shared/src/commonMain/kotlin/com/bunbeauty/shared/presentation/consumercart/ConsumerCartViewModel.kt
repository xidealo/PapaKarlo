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
import com.bunbeauty.shared.domain.feature.cart.model.Warning
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
        warningItem = null,
        cartProductItemList = emptyList(),
        recommendationList = emptyList(),
        oldTotalCost = null,
        newTotalCost = "",
        discount = null
    )
) {

    private var observeConsumerCartJob: Job? = null

    override fun reduce(action: ConsumerCart.Action, dataState: ConsumerCart.DataState) {
        when (action) {
            is ConsumerCart.Action.AddProductToCartClick -> increaseCartProductToCartClick(
                cartProductUuid = action.cartProductUuid,
                menuProductUuid = action.menuProductUuid
            )

            ConsumerCart.Action.BackClick -> navigateBack()
            ConsumerCart.Action.Init -> observeConsumerCart()
            ConsumerCart.Action.OnCreateOrderClick -> onCreateOrderClicked()
            ConsumerCart.Action.OnErrorButtonClick -> observeConsumerCart()
            ConsumerCart.Action.OnMenuClick -> onMenuClicked()
            is ConsumerCart.Action.OnProductClick -> onProductClicked(
                uuid = action.cartProductItem.menuProductUuid,
                name = action.cartProductItem.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.CART_PRODUCT,
                additionUuidList = action.cartProductItem.additionUuidList,
                cartProductUuid = action.cartProductItem.uuid
            )

            is ConsumerCart.Action.RemoveProductFromCartClick -> onRemoveCardProductClicked(
                menuProductUuid = action.menuProductUuid,
                cartProductUuid = action.cartProductUuid,
            )

            is ConsumerCart.Action.AddRecommendationProductToCartClick -> addRecommendationProductClicked(
                menuProductUuid = action.menuProductUuid,
            )

            is ConsumerCart.Action.RecommendationClick -> onProductClicked(
                uuid = action.menuProductUuid,
                name = action.name,
                productDetailsOpenedFrom = ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT,
                additionUuidList = emptyList(),
                cartProductUuid = null
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
                        warning = warning,
                        recommendationList = menuProductList
                    )
                }
            }.launchIn(sharedScope)
    }

    private fun ConsumerCart.DataState.copyWith(
        consumerCart: ConsumerCartDomain?,
        warning: Warning?,
        recommendationList: List<MenuProduct>
    ): ConsumerCart.DataState {
        return if (consumerCart is ConsumerCartDomain.WithProducts) {
            copy(
                state = ConsumerCart.DataState.State.SUCCESS,
                warningItem = warning?.toWarningItem(),
                cartProductItemList = consumerCart.cartProductList.mapIndexed { index, lightCartProduct ->
                    lightCartProduct.toCartProductItem(
                        isLast = index == consumerCart.cartProductList.lastIndex
                    )
                },
                oldTotalCost = consumerCart.oldTotalCost?.let { oldTotalCost ->
                    "$oldTotalCost $RUBLE_CURRENCY"
                },
                newTotalCost = "${consumerCart.newTotalCost} $RUBLE_CURRENCY",
                discount = discount?.let { discount ->
                    "$discount$PERCENT"
                },
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

    private fun onProductClicked(
        uuid: String,
        name: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        additionUuidList: List<String>,
        cartProductUuid: String?,
    ) {
        addEvent {
            ConsumerCart.Event.NavigateToProduct(
                uuid = uuid,
                name = name,
                productDetailsOpenedFrom = productDetailsOpenedFrom,
                additionUuidList = additionUuidList,
                cartProductUuid = cartProductUuid,
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

    private fun increaseCartProductToCartClick(cartProductUuid: String, menuProductUuid: String) {
        analyticService.sendEvent(
            event = IncreaseCartProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
            ),
        )
        sharedScope.launchSafe(
            block = {
                increaseCartProductCountUseCase(cartProductUuid = cartProductUuid)
            },
            onError = {
                addEvent {
                    ConsumerCart.Event.ShowAddProductError
                }
            }
        )
    }

    private fun onRemoveCardProductClicked(
        menuProductUuid: String,
        cartProductUuid: String,
    ) {
        handleRemoveAnalytic(menuProductUuid = menuProductUuid)
        sharedScope.launchSafe(
            block = {
                removeCartProductUseCase(
                    cartProductUuid = cartProductUuid,
                )
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

        if (dataState.value.getIsLastProduct(menuProductUuid = menuProductUuid)) {
            analyticService.sendEvent(
                event = RemoveCartProductClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
                ),
            )
        }
    }

}
