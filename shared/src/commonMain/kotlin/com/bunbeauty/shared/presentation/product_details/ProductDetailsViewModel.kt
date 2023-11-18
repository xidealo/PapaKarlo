package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.AddCartProductDetailsClickEvent
import com.bunbeauty.analytic.event.menu.AddMenuProductDetailsClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductDetailsClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductByUuidUseCase
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class ProductDetailsViewModel(
    private val getMenuProductByUuidUseCase: GetMenuProductByUuidUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val analyticService: AnalyticService,
) : SharedStateViewModel<ProductDetailsState.ViewDataState, ProductDetailsState.Action, ProductDetailsState.Event>(
    ProductDetailsState.ViewDataState(
        cartCostAndCount = null,
        menuProduct = null,
        screenState = ProductDetailsState.ViewDataState.ScreenState.LOADING,
    )
) {
    private var observeConsumerCartJob: Job? = null

    override fun reduce(
        action: ProductDetailsState.Action,
        dataState: ProductDetailsState.ViewDataState,
    ) {
        when (action) {
            is ProductDetailsState.Action.AddProductToCartClick -> onWantClicked(
                productDetailsOpenedFrom = action.productDetailsOpenedFrom
            )

            ProductDetailsState.Action.BackClick -> addEvent {
                ProductDetailsState.Event.NavigateBack
            }

            ProductDetailsState.Action.CartClick -> addEvent {
                ProductDetailsState.Event.NavigateToConsumerCart
            }

            is ProductDetailsState.Action.Init -> {
                observeCart()
                getMenuProduct(menuProductUuid = action.menuProductUuid)
            }
        }
    }

    fun getMenuProduct(
        menuProductUuid: String,
    ) {
        sharedScope.launchSafe(
            block = {
                val menuProduct = getMenuProductByUuidUseCase(menuProductUuid = menuProductUuid)
                setState {
                    if (menuProduct == null) {
                        copy(screenState = ProductDetailsState.ViewDataState.ScreenState.ERROR)
                    } else {
                        copy(
                            menuProduct = mapMenuProduct(menuProduct),
                            screenState = ProductDetailsState.ViewDataState.ScreenState.SUCCESS
                        )
                    }
                }
            },
            onError = {
                setState {
                    copy(screenState = ProductDetailsState.ViewDataState.ScreenState.ERROR)
                }
            }
        )
    }

    fun onWantClicked(
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        dataState.value.menuProduct?.let { menuProduct ->
            sendOnWantedClickedAnalytic(
                menuProductUuid = menuProduct.uuid,
                productDetailsOpenedFrom = productDetailsOpenedFrom
            )
            sharedScope.launchSafe(
                block = {
                    addCartProductUseCase(menuProduct.uuid)
                },
                onError = {
                    setState {
                        copy(screenState = ProductDetailsState.ViewDataState.ScreenState.ERROR)
                    }
                }
            )
        }
    }

    private fun sendOnWantedClickedAnalytic(
        menuProductUuid: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        analyticService.sendEvent(
            event = when (productDetailsOpenedFrom) {
                ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT -> AddRecommendationProductDetailsClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
                )

                ProductDetailsOpenedFrom.CART_PRODUCT -> AddCartProductDetailsClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
                )

                ProductDetailsOpenedFrom.MENU_PRODUCT -> AddMenuProductDetailsClickEvent(
                    menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid)
                )
            }
        )

    }

    private fun observeCart() {
        observeConsumerCartJob?.cancel()

        observeConsumerCartJob = sharedScope.launchSafe(
            block = {
                observeCartUseCase().collectLatest { cartTotalAndCount ->
                    setState {
                        copy(cartCostAndCount = cartTotalAndCount)
                    }
                }
            },
            onError = {
                setState {
                    copy(screenState = ProductDetailsState.ViewDataState.ScreenState.ERROR)
                }
            }
        )
    }

    private fun mapMenuProduct(menuProduct: MenuProduct): ProductDetailsState.ViewDataState.MenuProduct {
        return ProductDetailsState.ViewDataState.MenuProduct(
            uuid = menuProduct.uuid,
            photoLink = menuProduct.photoLink,
            name = menuProduct.name,
            size = if ((menuProduct.nutrition == null) || (menuProduct.utils == null)) {
                ""
            } else {
                "${menuProduct.nutrition} ${menuProduct.utils}"
            },
            oldPrice = menuProduct.oldPrice?.toString(),
            newPrice = menuProduct.newPrice.toString(),
            description = menuProduct.description,
        )
    }
}
