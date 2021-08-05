package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

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
        cartProductRepo.cartProductList.onEach { cartProductList ->
            val cartProductCount = productHelper.getTotalCount(cartProductList)
            mutableCartProductCount.value = cartProductCount.toString()
            val cartCost = productHelper.getNewTotalCost(cartProductList)
            mutableCartCost.value = stringUtil.getCostString(cartCost)
        }.launchIn(viewModelScope)
    }

    fun addProductToCart(menuProductUuid: String, menuProductRepo: MenuProductRepo) {
        viewModelScope.launch {
            val menuProduct = menuProductRepo.getMenuProduct(menuProductUuid) ?: return@launch
            val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
                cartProduct.menuProduct == menuProduct
            }
            if (cartProduct == null) {
                cartProductRepo.insert(CartProduct(menuProduct = menuProduct))
                showMessage(stringUtil.getAddedToCartString(menuProduct.name))
            } else {
                cartProduct.count++
                cartProductRepo.update(cartProduct)
            }
        }
    }

    fun removeProductFromCart(menuProductUuid: String, menuProductRepo: MenuProductRepo) {
        viewModelScope.launch {
            val menuProduct = menuProductRepo.getMenuProduct(menuProductUuid) ?: return@launch
            val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
                cartProduct.menuProduct == menuProduct
            } ?: return@launch
            if (cartProduct.count > 1) {
                cartProduct.count--
                cartProductRepo.update(cartProduct)
            } else {
                cartProductRepo.delete(cartProduct)
                showMessage(stringUtil.getRemovedFromCartString(menuProduct.name))
            }
        }
    }
}