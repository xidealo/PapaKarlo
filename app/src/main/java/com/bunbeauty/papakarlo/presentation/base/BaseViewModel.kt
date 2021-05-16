package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.domain.IMessageShowable
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.domain.repository.cart_product.CartProductRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    @Inject
    lateinit var cartProductRepo: CartProductRepo

    @Inject
    lateinit var router: Router

    var messageShowable: WeakReference<IMessageShowable>? = null

    val isCartEmptyLiveData = MutableLiveData(false)

    val cartProductListLiveData by lazy {
        map(cartProductRepo.getCartProductListFlow().asLiveData()) { productList ->
            isCartEmptyLiveData.value = productList.isEmpty()
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
        showMessage("Вы добавили ${menuProduct.name} в корзину")
    }


    fun showMessage(message: String) {
        messageShowable?.get()?.showMessage(message)
    }

    fun showError(error: String) {
        messageShowable?.get()?.showError(error)
    }
}