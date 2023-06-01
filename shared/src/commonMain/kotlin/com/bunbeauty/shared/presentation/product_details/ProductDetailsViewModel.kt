package com.bunbeauty.shared.presentation.product_details

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductByUuidUseCase
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val getMenuProductByUuidUseCase: GetMenuProductByUuidUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val addCartProductUseCase: AddCartProductUseCase,
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

    fun getMenuProduct(menuProductUuid: String) {
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

    fun onWantClicked() {
        menuProductDetailsState.value.menuProduct?.let { menuProduct ->
            sharedScope.launch {
                addCartProductUseCase(menuProduct.uuid)
            }
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
