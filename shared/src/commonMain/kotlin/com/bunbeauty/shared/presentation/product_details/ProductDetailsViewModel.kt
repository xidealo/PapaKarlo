package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.analytic.AnalyticService
import com.bunbeauty.analytic.event.CartAddEvent
import com.bunbeauty.analytic.event.EventParameter
import com.bunbeauty.analytic.event.MenuAddEvent
import com.bunbeauty.analytic.event.RecommendationAddEvent
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductByUuidUseCase
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val getMenuProductByUuidUseCase: GetMenuProductByUuidUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val analyticService: AnalyticService,
) : SharedViewModel() {

    private val mutableProductDetailsState = MutableStateFlow(ProductDetailsState())
    val menuProductDetailsState = mutableProductDetailsState.asCommonStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        mutableProductDetailsState.update { state ->
            state.copy(state = ProductDetailsState.State.ERROR)
        }
    }

    init {
        observeCart()
    }

    fun getMenuProduct(
        menuProductUuid: String,
    ) {
        sharedScope.launch(exceptionHandler) {
            val menuProduct = getMenuProductByUuidUseCase(menuProductUuid)
            mutableProductDetailsState.update { state ->
                if (menuProduct == null) {
                    state.copy(state = ProductDetailsState.State.ERROR)
                } else {
                    state.copy(
                        menuProduct = mapMenuProduct(menuProduct),
                        state = ProductDetailsState.State.SUCCESS
                    )
                }
            }
        }
    }

    fun onWantClicked(
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        menuProductDetailsState.value.menuProduct?.let { menuProduct ->
            sendOnWantedClickedAnalytic(
                menuProductUuid = menuProduct.uuid,
                productDetailsOpenedFrom = productDetailsOpenedFrom
            )
            sharedScope.launch {
                addCartProductUseCase(menuProduct.uuid)
            }
        }
    }

    private fun sendOnWantedClickedAnalytic(
        menuProductUuid: String,
        productDetailsOpenedFrom: ProductDetailsOpenedFrom,
    ) {
        when (productDetailsOpenedFrom) {
            ProductDetailsOpenedFrom.RECOMMENDATION_PRODUCT -> analyticService.sendEvent(
                RecommendationAddEvent,
                params = listOf(EventParameter("menuProductUuid", menuProductUuid))
            )

            ProductDetailsOpenedFrom.CART_PRODUCT -> analyticService.sendEvent(
                CartAddEvent,
                params = listOf(EventParameter("menuProductUuid", menuProductUuid))
            )

            ProductDetailsOpenedFrom.MENU_PRODUCT -> analyticService.sendEvent(
                MenuAddEvent,
                params = listOf(EventParameter("menuProductUuid", menuProductUuid))
            )
        }
    }

    private fun observeCart() {
        sharedScope.launch(exceptionHandler) {
            observeCartUseCase().collectLatest { cartTotalAndCount ->
                mutableProductDetailsState.update { state ->
                    state.copy(cartCostAndCount = cartTotalAndCount)
                }
            }
        }
    }

    private fun mapMenuProduct(menuProduct: MenuProduct): ProductDetailsState.MenuProduct {
        return ProductDetailsState.MenuProduct(
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
