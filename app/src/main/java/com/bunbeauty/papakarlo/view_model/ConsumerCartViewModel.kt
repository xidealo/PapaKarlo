package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductRepo
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.ui.consumer_cart.ConsumerCartNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConsumerCartViewModel @Inject constructor() : BaseViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    var navigator: WeakReference<ConsumerCartNavigator>? = null

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            "${productList.sumBy { getFullPrice(it) }} ₽"
        }
    }

    private fun getFullPrice(cartProduct: CartProduct): Int {
        return cartProduct.menuProduct.cost * cartProduct.count
    }

    fun updateCartProduct(cartProduct: CartProduct) {
        if (cartProduct.count > 0) {
            launch(Dispatchers.IO) {
                cartProductRepo.update(cartProduct)
            }
        } else {
            launch(Dispatchers.IO) {
                cartProductRepo.delete(cartProduct)
            }
        }
    }

}