package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base class for each viewModel which has top bar cart or can add product to cart
 */
open class CartViewModel : BaseViewModel() {

    @Inject
    @Firebase
    lateinit var baseCartProductRepo: CartProductRepo

    @Inject
    lateinit var baseStringUtil: IStringUtil

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    @Inject
    fun subscribeOnCartProductList(productHelper: IProductHelper) {
        baseCartProductRepo.observeCartProductList().onEach { cartProductList ->
            val cartProductCount = productHelper.getTotalCount(cartProductList)

            mutableCartProductCount.value = cartProductCount.toString()
            val cartCost = productHelper.getNewTotalCost(cartProductList)
            mutableCartCost.value = baseStringUtil.getCostString(cartCost)
        }.launchIn(viewModelScope)
    }

    fun addProductToCart(menuProductUuid: String) {
        viewModelScope.launch {
            val cartProduct = baseCartProductRepo.getCartProductByMenuProductUuid(menuProductUuid)
            if (cartProduct == null) {
                val savedCartProduct = baseCartProductRepo.saveAsCartProduct(menuProductUuid)
                if (savedCartProduct != null) {
                    showMessage(baseStringUtil.getAddedToCartString(savedCartProduct.menuProduct.name))
                }
            } else {
                baseCartProductRepo.updateCount(cartProduct.uuid, cartProduct.count + 1)
            }
        }
    }

    fun removeProductFromCart(menuProductUuid: String) {
        viewModelScope.launch {
            val cartProduct = baseCartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) ?: return@launch
            if (cartProduct.count > 1) {
                baseCartProductRepo.updateCount(cartProduct.uuid, cartProduct.count - 1)
            } else {
                baseCartProductRepo.deleteCartProduct(cartProduct)
                showMessage(baseStringUtil.getRemovedFromCartString(cartProduct.menuProduct.name))
            }
        }
    }
}