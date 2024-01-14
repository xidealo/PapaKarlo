package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.cart.AddCartProductDetailsClickEvent
import com.bunbeauty.analytic.event.menu.AddMenuProductDetailsClickEvent
import com.bunbeauty.analytic.event.recommendation.AddRecommendationProductDetailsClickEvent
import com.bunbeauty.analytic.parameter.MenuProductUuidEventParameter
import com.bunbeauty.shared.Constants.RUBLE_CURRENCY
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class ProductDetailsViewModel(
    private val getMenuProductUseCase: GetMenuProductUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val analyticService: AnalyticService,
    private val editCartProductUseCase: EditCartProductUseCase,
) : SharedStateViewModel<ProductDetailsState.DataState, ProductDetailsState.Action, ProductDetailsState.Event>(
    ProductDetailsState.DataState(
        cartCostAndCount = null,
        menuProduct = ProductDetailsState.DataState.MenuProduct(
            uuid = "",
            photoLink = "",
            name = "",
            size = "",
            oldPrice = null,
            newPrice = 0,
            priceWithAdditions = 0,
            description = "",
            additionGroups = listOf(),
            currency = RUBLE_CURRENCY
        ),
        screenState = ProductDetailsState.DataState.ScreenState.INIT,
    )
) {
    private var observeConsumerCartJob: Job? = null

    override fun reduce(
        action: ProductDetailsState.Action,
        dataState: ProductDetailsState.DataState,
    ) {
        when (action) {
            is ProductDetailsState.Action.AddProductToCartClick -> onWantClicked(
                productDetailsOpenedFrom = action.productDetailsOpenedFrom,
                cartProductUuid = action.cartProductUuid,
            )

            ProductDetailsState.Action.BackClick -> addEvent {
                ProductDetailsState.Event.NavigateBack
            }

            ProductDetailsState.Action.CartClick -> addEvent {
                ProductDetailsState.Event.NavigateToConsumerCart
            }

            is ProductDetailsState.Action.AdditionClick -> selectAddition(
                uuid = action.uuid,
                groupUuid = action.groupUuid
            )

            is ProductDetailsState.Action.Init -> {
                observeCart()
                getMenuProduct(
                    menuProductUuid = action.menuProductUuid,
                    selectedAdditionUuidList = action.selectedAdditionUuidList
                )
            }
        }
    }

    private fun selectAddition(uuid: String, groupUuid: String) {
        setState {
            val changedGroup = getChangedGroup(menuProduct, groupUuid = groupUuid, uuid = uuid)

            val newAdditionGroups = menuProduct.additionGroups.mapNotNull { additionGroup ->
                if (additionGroup.uuid == groupUuid) {
                    changedGroup
                } else {
                    additionGroup
                }
            }

            copy(
                menuProduct = menuProduct.copy(
                    additionGroups = newAdditionGroups,
                    priceWithAdditions = (
                            menuProduct.newPrice + newAdditionGroups
                                .flatMap { it.additionList }
                                .filter { addition -> addition.isSelected }
                                .sumOf { addition -> addition.price ?: 0 }
                            )
                )
            )
        }
    }

    private fun getChangedGroup(
        menuProduct: ProductDetailsState.DataState.MenuProduct,
        groupUuid: String,
        uuid: String,
    ): AdditionGroup? {
        return menuProduct.additionGroups.find { it.uuid == groupUuid }
            ?.let { additionGroup ->
                additionGroup.copy(
                    additionList = if (additionGroup.singleChoice) {
                        additionGroup.additionList.map { addition ->
                            addition.copy(
                                isSelected = addition.uuid == uuid
                            )
                        }
                    } else {
                        additionGroup.additionList.map { addition ->
                            addition.copy(
                                isSelected = if (addition.uuid == uuid) {
                                    !addition.isSelected
                                } else {
                                    addition.isSelected
                                }
                            )
                        }
                    }
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
                            menuProduct = mapMenuProduct(
                                menuProduct = menuProduct,
                                selectedAdditionUuidList = selectedAdditionUuidList,
                                isInit = screenState == ProductDetailsState.DataState.ScreenState.INIT,
                                stateAdditionGroupList = this.menuProduct.additionGroups
                            ),
                            screenState = ProductDetailsState.DataState.ScreenState.SUCCESS
                        )
                    }
                }
            },
            onError = {
                setState {
                    copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                }
            }
        )
    }

    private fun onWantClicked(
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
        cartProductUuid: String?,
    ) {

        val menuProduct = dataState.value.menuProduct

        sendOnWantedClickedAnalytic(
            menuProductUuid = menuProduct.uuid,
            productDetailsOpenedFrom = productDetailsOpenedFrom
        )

        sharedScope.launchSafe(
            block = {
                val selectedAdditionUuidList = menuProduct.additionList
                    .filter { addition -> addition.isSelected }
                    .map { addition -> addition.uuid }

                if (productDetailsOpenedFrom == ProductDetailsOpenedFrom.CART_PRODUCT) {
                    cartProductUuid?.let { cartProductUuid ->
                        editCartProductUseCase(
                            cartProductUuid = cartProductUuid,
                            additionUuidList = selectedAdditionUuidList,
                        )
                        addEvent {
                            ProductDetailsState.Event.EditedProduct(menuProductName = menuProduct.name)
                        }
                    }
                } else {
                    addCartProductUseCase(
                        menuProductUuid = menuProduct.uuid,
                        additionUuidList = selectedAdditionUuidList
                    )
                    addEvent {
                        ProductDetailsState.Event.AddedProduct(menuProductPhotoLink = menuProduct.photoLink)
                    }
                }
            },
            onError = {
                setState {
                    copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                }
            }
        )
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
                    copy(screenState = ProductDetailsState.DataState.ScreenState.ERROR)
                }
            }
        )
    }

    private fun mapMenuProduct(
        menuProduct: MenuProduct,
        selectedAdditionUuidList: List<String>,
        isInit: Boolean,
        stateAdditionGroupList: List<AdditionGroup>,
    ): ProductDetailsState.DataState.MenuProduct {

        val groupWithSelectedAdditionFromConsumerCart = when {
            !isInit -> stateAdditionGroupList
            selectedAdditionUuidList.isEmpty() -> menuProduct.additionGroups
            else -> getAdditionGroupsWithSelectedAddition(
                menuProduct = menuProduct,
                selectedAdditionUuidList = selectedAdditionUuidList
            )
        }

        return ProductDetailsState.DataState.MenuProduct(
            uuid = menuProduct.uuid,
            photoLink = menuProduct.photoLink,
            name = menuProduct.name,
            size = if ((menuProduct.nutrition == null) || (menuProduct.utils == null)) {
                ""
            } else {
                "${menuProduct.nutrition} ${menuProduct.utils}"
            },
            oldPrice = menuProduct.oldPrice,
            newPrice = menuProduct.newPrice,
            description = menuProduct.description,
            priceWithAdditions =
            menuProduct.newPrice + groupWithSelectedAdditionFromConsumerCart
                .flatMap { additionGroup ->
                    additionGroup.additionList
                }
                .filter { addition -> addition.isSelected }
                .sumOf { addition -> addition.price ?: 0 },
            additionGroups = groupWithSelectedAdditionFromConsumerCart,
            currency = RUBLE_CURRENCY
        )
    }

    private fun getAdditionGroupsWithSelectedAddition(
        menuProduct: MenuProduct,
        selectedAdditionUuidList: List<String>,
    ) = menuProduct.additionGroups.map { additionGroup ->
        additionGroup.copy(
            additionList = additionGroup.additionList.map { addition ->
                addition.copy(
                    isSelected = selectedAdditionUuidList.any { additionUuid ->
                        additionUuid == addition.uuid
                    }
                )
            }
        )
    }

}
