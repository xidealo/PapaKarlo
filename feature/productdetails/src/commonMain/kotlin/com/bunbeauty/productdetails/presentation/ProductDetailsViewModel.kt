package com.bunbeauty.productdetails.presentation

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.AddCartProductDetailsClickEvent
import com.bunbeauty.analytic.event.favorite.AddFavoriteClickEvent
import com.bunbeauty.analytic.event.favorite.RemoveFavoriteClickEvent
import com.bunbeauty.analytic.event.menu.AddMenuProductDetailsClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductDetailsClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.core.Constants.RUBLE_CURRENCY
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.ObserveCartUseCase
import com.bunbeauty.core.domain.addition.GetAdditionGroupsWithSelectedAdditionUseCase
import com.bunbeauty.core.domain.addition.GetPriceOfSelectedAdditionsUseCase
import com.bunbeauty.core.domain.auth.ObserveTokenUseCase
import com.bunbeauty.core.domain.cart.AddCartProductUseCase
import com.bunbeauty.core.domain.cart.EditCartProductUseCase
import com.bunbeauty.core.domain.favorite.IsProductFavoriteUseCase
import com.bunbeauty.core.domain.favorite.LoadFavoritesUseCase
import com.bunbeauty.core.domain.favorite.ToggleFavoriteUseCase
import com.bunbeauty.core.domain.menu_product.GetMenuProductUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.ProductDetailsOpenedFrom
import com.bunbeauty.core.model.addition.AdditionGroup
import com.bunbeauty.core.model.product.MenuProduct
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class ProductDetailsViewModel(
    private val getMenuProductUseCase: GetMenuProductUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val analyticService: AnalyticService,
    private val editCartProductUseCase: EditCartProductUseCase,
    private val getAdditionGroupsWithSelectedAdditionUseCase: GetAdditionGroupsWithSelectedAdditionUseCase,
    private val getSelectedAdditionsPriceUseCase: GetPriceOfSelectedAdditionsUseCase,
    private val observeTokenUseCase: ObserveTokenUseCase,
    private val loadFavoritesUseCase: LoadFavoritesUseCase,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : SharedStateViewModel<ProductDetailsState.DataState, ProductDetailsState.Action, ProductDetailsState.Event>(
        ProductDetailsState.DataState(
            cartCostAndCount = null,
            menuProduct =
                ProductDetailsState.DataState.MenuProduct(
                    uuid = "",
                    photoLink = "",
                    name = "",
                    size = "",
                    oldPrice = null,
                    newPrice = 0,
                    priceWithAdditions = 0,
                    description = "",
                    additionGroups = listOf(),
                    currency = RUBLE_CURRENCY,
                ),
            screenState = ProductDetailsState.DataState.ScreenState.INIT,
        ),
    ) {
    companion object {
        /** Groups with at most this many additions use row layout; larger groups use horizontal card layout. */
        const val MAX_ADDITION_COUNT_FOR_ROW_LAYOUT = 3
    }

    private var observeConsumerCartJob: Job? = null
    private var observeTokenJob: Job? = null
    private var currentMenuProductUuid: String = ""

    override fun reduce(
        action: ProductDetailsState.Action,
        dataState: ProductDetailsState.DataState,
    ) {
        when (action) {
            is ProductDetailsState.Action.AddProductToCartClick ->
                onWantClicked(
                    productDetailsOpenedFrom = action.productDetailsOpenedFrom,
                    cartProductUuid = action.cartProductUuid,
                )

            ProductDetailsState.Action.BackClick ->
                addEvent {
                    ProductDetailsState.Event.NavigateBack
                }

            is ProductDetailsState.Action.AdditionClick ->
                selectAddition(
                    uuid = action.uuid,
                    groupUuid = action.groupUuid,
                )

            is ProductDetailsState.Action.Init -> {
                currentMenuProductUuid = action.menuProductUuid
                observeCart()
                observeToken()
                getMenuProduct(
                    menuProductUuid = action.menuProductUuid,
                    selectedAdditionUuidList = action.selectedAdditionUuidList,
                )
            }

            ProductDetailsState.Action.FavoriteClick -> onFavoriteClick()
        }
    }

    private fun onFavoriteClick() {
        val menuProductUuid = dataState.value.menuProduct.uuid
        val previousFavorite = dataState.value.isFavorite
        setState {
            copy(isFavorite = !isFavorite)
        }
        sharedScope.launchSafe(
            block = {
                val isFavorite = toggleFavoriteUseCase(menuProductUuid = menuProductUuid)
                sendFavoriteToggleAnalytic(
                    menuProductUuid = menuProductUuid,
                    isFavorite = isFavorite,
                )
                setState {
                    copy(isFavorite = isFavorite)
                }
            },
            onError = {
                setState {
                    copy(isFavorite = previousFavorite)
                }
                addEvent {
                    ProductDetailsState.Event.ShowFavoriteError
                }
            },
        )
    }

    private fun sendFavoriteToggleAnalytic(
        menuProductUuid: String,
        isFavorite: Boolean,
    ) {
        val menuProductUuidEventParameter =
            MenuProductUuidEventParameter(value = menuProductUuid)
        analyticService.sendEvent(
            event =
                if (isFavorite) {
                    AddFavoriteClickEvent(
                        menuProductUuidEventParameter = menuProductUuidEventParameter,
                    )
                } else {
                    RemoveFavoriteClickEvent(
                        menuProductUuidEventParameter = menuProductUuidEventParameter,
                    )
                },
        )
    }

    private fun observeToken() {
        observeTokenJob?.cancel()

        observeTokenJob =
            sharedScope.launchSafe(
                block = {
                    observeTokenUseCase().collectLatest { token ->
                        val isAuthorized = token != null
                        setState {
                            copy(isAuthorized = isAuthorized)
                        }
                        if (isAuthorized) {
                            loadFavoriteState(menuProductUuid = currentMenuProductUuid)
                        } else {
                            setState {
                                copy(isFavorite = false)
                            }
                        }
                    }
                },
                onError = { },
            )
    }

    private fun loadFavoriteState(menuProductUuid: String) {
        if (menuProductUuid.isEmpty()) {
            return
        }

        sharedScope.launchSafe(
            block = {
                loadFavoritesUseCase()
                val isFavorite = isProductFavoriteUseCase(menuProductUuid = menuProductUuid)
                setState {
                    copy(isFavorite = isFavorite)
                }
            },
            onError = { },
        )
    }

    private fun selectAddition(
        uuid: String,
        groupUuid: String,
    ) {
        setState {
            val newAdditionGroups =
                getAdditionGroupsWithSelectedAdditionUseCase(
                    additionGroups = menuProduct.additionGroups,
                    groupUuid = groupUuid,
                    additionUuid = uuid,
                )
            val selectedAdditionsPrice =
                getSelectedAdditionsPriceUseCase(
                    additions = newAdditionGroups.flatMap { it.additionList },
                )

            copy(
                menuProduct =
                    menuProduct.copy(
                        additionGroups = newAdditionGroups,
                        priceWithAdditions = (menuProduct.newPrice + selectedAdditionsPrice),
                    ),
            )
        }
    }

    private fun getMenuProduct(
        menuProductUuid: String,
        selectedAdditionUuidList: List<String>,
    ) {
        sharedScope.launchSafe(
            block = {
                val menuProduct = getMenuProductUseCase(menuProductUuid = menuProductUuid)
                setState {
                    if (menuProduct == null) {
                        copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                    } else {
                        copy(
                            menuProduct =
                                mapMenuProduct(
                                    menuProduct = menuProduct,
                                    selectedAdditionUuidList = selectedAdditionUuidList,
                                    isInit = screenState == ProductDetailsState.DataState.ScreenState.INIT,
                                    stateAdditionGroupList = this.menuProduct.additionGroups,
                                ),
                            screenState = ProductDetailsState.DataState.ScreenState.SUCCESS,
                        )
                    }
                }
            },
            onError = {
                setState {
                    copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                }
            },
        )
    }

    private fun onWantClicked(
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        cartProductUuid: String?,
    ) {
        val menuProduct = dataState.value.menuProduct

        sendOnWantedClickedAnalytic(
            menuProductUuid = menuProduct.uuid,
            productDetailsOpenedFrom = productDetailsOpenedFrom,
        )

        sharedScope.launchSafe(
            block = {
                val selectedAdditionList =
                    menuProduct.additionList
                        .filter { addition ->
                            addition.isSelected
                        }

                if (productDetailsOpenedFrom == ProductDetailsOpenedFrom.CART_PRODUCT) {
                    cartProductUuid?.let { cartProductUuid ->
                        editCartProductUseCase(
                            cartProductUuid = cartProductUuid,
                            additionList = selectedAdditionList,
                        )
                        addEvent {
                            ProductDetailsState.Event.EditedProduct
                        }
                    }
                } else {
                    addCartProductUseCase(
                        menuProductUuid = menuProduct.uuid,
                        additionUuidList =
                            selectedAdditionList
                                .map { addition ->
                                    addition.uuid
                                },
                    )
                    addEvent {
                        ProductDetailsState.Event.AddedProduct
                    }
                }
            },
            onError = {
                addEvent { ProductDetailsState.Event.ShowAddProductError }
            },
        )
    }

    private fun sendOnWantedClickedAnalytic(
        menuProductUuid: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        analyticService.sendEvent(
            event =
                when (productDetailsOpenedFrom) {
                    ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT ->
                        AddRecommendationProductDetailsClickEvent(
                            menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                        )

                    ProductDetailsOpenedFrom.CART_PRODUCT ->
                        AddCartProductDetailsClickEvent(
                            menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                        )

                    ProductDetailsOpenedFrom.MENU_PRODUCT ->
                        AddMenuProductDetailsClickEvent(
                            menuProductUuidEventParameter = MenuProductUuidEventParameter(value = menuProductUuid),
                        )
                },
        )
    }

    private fun observeCart() {
        observeConsumerCartJob?.cancel()

        observeConsumerCartJob =
            sharedScope.launchSafe(
                block = {
                    observeCartUseCase().collectLatest { cartTotalAndCount ->
                        setState {
                            copy(cartCostAndCount = cartTotalAndCount)
                        }
                    }
                },
                onError = {
                    setState {
                        copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                    }
                },
            )
    }

    private fun mapMenuProduct(
        menuProduct: MenuProduct,
        selectedAdditionUuidList: List<String>,
        isInit: Boolean,
        stateAdditionGroupList: List<AdditionGroup>,
    ): ProductDetailsState.DataState.MenuProduct {
        val groupWithSelectedAdditionFromConsumerCart =
            when {
                !isInit -> stateAdditionGroupList
                selectedAdditionUuidList.isEmpty() -> menuProduct.additionGroups
                else ->
                    getAdditionGroupsWithSelectedAddition(
                        menuProduct = menuProduct,
                        selectedAdditionUuidList = selectedAdditionUuidList,
                    )
            }
        val selectedAdditionsPrice =
            getSelectedAdditionsPriceUseCase(
                additions =
                    groupWithSelectedAdditionFromConsumerCart.flatMap { additionGroup ->
                        additionGroup.additionList
                    },
            )

        return ProductDetailsState.DataState.MenuProduct(
            uuid = menuProduct.uuid,
            photoLink = menuProduct.photoLink,
            name = menuProduct.name,
            size =
                if ((menuProduct.nutrition == null) || (menuProduct.utils == null)) {
                    ""
                } else {
                    "${menuProduct.nutrition} ${menuProduct.utils}"
                },
            oldPrice = menuProduct.oldPrice,
            newPrice = menuProduct.newPrice,
            description = menuProduct.description,
            priceWithAdditions = menuProduct.newPrice + selectedAdditionsPrice,
            additionGroups = groupWithSelectedAdditionFromConsumerCart,
            currency = RUBLE_CURRENCY,
        )
    }

    private fun getAdditionGroupsWithSelectedAddition(
        menuProduct: MenuProduct,
        selectedAdditionUuidList: List<String>,
    ) = menuProduct.additionGroups.map { additionGroup ->
        additionGroup.copy(
            additionList =
                additionGroup.additionList.map { addition ->
                    addition.copy(
                        isSelected = selectedAdditionUuidList.contains(addition.uuid),
                    )
                },
        )
    }
}
