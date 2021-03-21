package com.bunbeauty.papakarlo.view_model.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.ui.base.IMessageShowable
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartFragmentDirections.toCreationOrder
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
        map(cartProductRepo.getCartProductListLiveData()) { productList ->
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