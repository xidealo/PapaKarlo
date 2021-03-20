package com.bunbeauty.papakarlo.view_model.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    val isCartEmptyLiveData = MutableLiveData(false)

    val cartProductListLiveData by lazy {
        map(cartProductRepo.getCartProductListLiveData()) { productList ->
            isCartEmptyLiveData.value = productList.isEmpty()
            productList
        }
    }

    fun addProductToCart(menuProduct: com.bunbeauty.data.model.MenuProduct) = launch(Dispatchers.IO) {
        val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }

        if (cartProduct == null) {
            cartProductRepo.insert(com.bunbeauty.data.model.CartProduct(menuProduct = menuProduct))
        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
    }
}