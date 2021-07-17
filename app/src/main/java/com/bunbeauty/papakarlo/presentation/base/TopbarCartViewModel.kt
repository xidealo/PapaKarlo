package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.MenuProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Base class for each viewModel which has top bar cart or can add product to cart
 */
abstract class TopbarCartViewModel(
    private val cartProductRepo: CartProductRepo,
    private val stringUtil: IStringHelper,
    val productHelper: IProductHelper,
) : BaseViewModel() {

    @Inject
    lateinit var menuProductRepo: MenuProductRepo

    private val mutableCartCost: MutableStateFlow<String> = MutableStateFlow("")
    val cartCost: StateFlow<String> = mutableCartCost.asStateFlow()

    private val mutableCartProductCount: MutableStateFlow<String> = MutableStateFlow("")
    val cartProductCount: StateFlow<String> = mutableCartProductCount.asStateFlow()

    init {
        subscribeOnCartProductList()
    }

    private fun subscribeOnCartProductList() {
        cartProductRepo.getCartProductListFlow().onEach { cartProductList ->
            val cartProductCount = productHelper.getTotalCount(cartProductList)
            mutableCartProductCount.value = cartProductCount.toString()
            val cartCost = productHelper.getNewTotalCost(cartProductList)
            mutableCartCost.value = stringUtil.getCostString(cartCost)
        }.launchIn(viewModelScope)
    }

    fun addProductToCart(menuProductUuid: String) = viewModelScope.launch {
        val menuProduct = menuProductRepo.getMenuProduct(menuProductUuid) ?: return@launch
        val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }
        if (cartProduct == null) {
            cartProductRepo.insertToLocal(
                CartProduct(
                    uuid = UUID.randomUUID().toString(),
                    menuProduct = menuProduct
                )
            )
        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
        showMessage(stringUtil.getAddedToCartString(menuProduct.name))
    }
}