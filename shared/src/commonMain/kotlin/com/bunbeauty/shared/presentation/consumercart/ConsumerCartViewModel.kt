package com.bunbeauty.shared.presentation.consumercart

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.DecreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.IncreaseCartProductClickEvent
import com.bunbeauty.analytic.event.cart.RemoveCartProductClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.core.Logger
import com.bunbeauty.shared.Constants.PERCENT
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
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
            is ConsumerCart.Action.AddProductToCartClick -> addCartProductToCartClick(
                menuProductUuid = action.menuProductUuid,
                additionUuidList = action.additionUuidList
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
                menuProductUuid = action.menuProductUuid
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
            copy(screenState = ConsumerCart.ViewDataState.ScreenState.LOADING)
        }
        observeConsumerCartJob?.cancel()
        observeConsumerCartJob =
            cartProductInteractor.observeConsumerCart().onEach { consumerCartDomain ->
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
        analyticService.sendEvent(
            event = AddRecommendationProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
            ),
        )
        addProduct(
            menuProductUuid = menuProductUuid,
            additionUuidList = emptyList()
        )
    }

    private fun addCartProductToCartClick(
        menuProductUuid: String,
        additionUuidList: List<String>,
    ) {
        analyticService.sendEvent(
            event = IncreaseCartProductClickEvent(
                menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
            ),
        )
        addProduct(
            menuProductUuid = menuProductUuid,
            additionUuidList = additionUuidList
        )
    }

    private fun addProduct(
        menuProductUuid: String,
        additionUuidList: List<String>,
    ) {
        sharedScope.launchSafe(
            block = {
                addCartProductUseCase(
                    menuProductUuid = menuProductUuid,
                    additionUuidList = additionUuidList
                )
            },
            onError = {
                // TODO handle error
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
                // TODO handle error
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

    private suspend fun getConsumerCartData(
        consumerCartDomain: ConsumerCartDomain,
    ): ConsumerCart.ViewDataState.ConsumerCartData? {
        return when (consumerCartDomain) {
            is ConsumerCartDomain.Empty -> null
            is ConsumerCartDomain.WithProducts -> ConsumerCart.ViewDataState.ConsumerCartData(
                forFreeDelivery = "${consumerCartDomain.forFreeDelivery}$RUBLE_CURRENCY",
                cartProductList = consumerCartDomain.cartProductList.mapIndexed { index, lightCartProduct ->
                    toItem(
                        lightCartProduct = lightCartProduct,
                        isLast = index == consumerCartDomain.cartProductList.lastIndex
                    )
                },
                oldTotalCost = consumerCartDomain.oldTotalCost?.let { oldTotalCost ->
                    oldTotalCost.toString() + RUBLE_CURRENCY
                },
                newTotalCost = consumerCartDomain.newTotalCost.toString() + RUBLE_CURRENCY,
                firstOrderDiscount = consumerCartDomain.discount?.let { discount ->
                    "$discount$PERCENT"
                },
                recommendations = getRecommendationsUseCase().map { menuProduct ->
                    with(menuProduct) {
                        MenuProductItem(
                            uuid = uuid,
                            photoLink = photoLink,
                            name = name,
                            oldPrice = oldPrice,
                            newPrice = newPrice,
                            hasAdditions = additionGroups.isNotEmpty()
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

    private fun toItem(lightCartProduct: LightCartProduct, isLast: Boolean): CartProductItem {
        return CartProductItem(
            uuid = lightCartProduct.uuid,
            name = lightCartProduct.name,
            newCost = "${lightCartProduct.newCost}$RUBLE_CURRENCY",
            oldCost = lightCartProduct.oldCost?.let { oldCost -> "$oldCost$RUBLE_CURRENCY" },
            photoLink = lightCartProduct.photoLink,
            count = lightCartProduct.count,
            menuProductUuid = lightCartProduct.menuProductUuid,
            additions = lightCartProduct.cartProductAdditionList
                .joinToString(" • ") { cartProductAddition ->
                    cartProductAddition.fullName ?: cartProductAddition.name
                }
                .ifEmpty { null },
            additionUuidList = lightCartProduct.cartProductAdditionList
                .map { cartProductAddition -> cartProductAddition.additionUuid },
            isLast = isLast
        )
    }

}
