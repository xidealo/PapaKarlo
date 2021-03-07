package com.bunbeauty.papakarlo.view_model.base

import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    val isCartEmpty = ObservableField(false)

    val cartProductListLiveData by lazy {
        map(cartProductRepo.getCartProductListLiveData()) { productList ->
            isCartEmpty.set(productList.isEmpty())
            productList
        }
    }

    fun addProductToCart(menuProduct: MenuProduct) = launch(Dispatchers.IO) {
        val cartProduct = cartProductRepo.getCartProductList().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }

        if (cartProduct == null) {
            cartProductRepo.insert(CartProduct(menuProduct = menuProduct))
        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
    }
}