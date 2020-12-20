package com.bunbeauty.papakarlo.view_model.base

import androidx.lifecycle.ViewModel
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.ui.base.BaseNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<N : BaseNavigator> : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    abstract var navigator: WeakReference<N>?

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    val cartProductListLiveData by lazy {
        cartProductRepo.getCartProductList()
    }

    fun addProductToCart(menuProduct: MenuProduct) = launch(Dispatchers.IO) {
        val cartProduct = cartProductRepo.getCartProductListAsync().await().find { cartProduct ->
            cartProduct.menuProduct == menuProduct
        }

        if (cartProduct == null) {
            cartProductRepo.insert(CartProduct(menuProduct = menuProduct))

        } else {
            cartProduct.count++
            cartProductRepo.update(cartProduct)
        }
    }

    fun showMessage(message: String) {
        navigator?.get()?.showMessage(message)
    }
}