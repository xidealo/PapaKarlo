package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Base class for each viewModel which has top bar cart or can add product to cart
 */
abstract class CartViewModel(
    protected val cartProductRepo: CartProductRepo,
    protected val stringUtil: IStringUtil,
    protected val productHelper: IProductHelper,
) : BaseViewModel() {

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    init {
        subscribeOnCartProductList()
    }

    private fun subscribeOnCartProductList() {
        cartProductRepo.observeCartProductList().onEach { cartProductList ->
            val cartProductCount = productHelper.getTotalCount(cartProductList)
            mutableCartProductCount.value = cartProductCount.toString()
            val cartCost = productHelper.getNewTotalCost(cartProductList)
            mutableCartCost.value = stringUtil.getCostString(cartCost)
        }.launchIn(viewModelScope)
    }

    fun addProductToCart(menuProductUuid: String) {
        viewModelScope.launch {
            val cartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid)
            if (cartProduct == null) {
                val savedCartProduct = cartProductRepo.saveAsCartProduct(menuProductUuid)
                if (savedCartProduct != null) {
                    showMessage(stringUtil.getAddedToCartString(savedCartProduct.menuProduct.name))
                }
            } else {
                cartProductRepo.updateCount(cartProduct.uuid, cartProduct.count + 1)
            }
        }
    }

    fun removeProductFromCart(menuProductUuid: String) {
        viewModelScope.launch {
            val cartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) ?: return@launch
            if (cartProduct.count > 1) {
                cartProductRepo.updateCount(cartProduct.uuid, cartProduct.count - 1)
            } else {
                cartProductRepo.deleteCartProduct(cartProduct)
                showMessage(stringUtil.getRemovedFromCartString(cartProduct.menuProduct.name))
            }
        }
    }
}