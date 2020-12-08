package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.local.db.CartDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.ui.ConsumerCartNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConsumerCartViewModel @Inject constructor(private val cartDao: CartDao) : BaseViewModel(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    lateinit var consumerCartNavigator: WeakReference<ConsumerCartNavigator>

    val cartProductListLiveData by lazy {
        cartDao.getCart()
    }

    fun updateCartProduct(cartProduct: CartProduct) {
        if (cartProduct.count > 0) {
            launch(Dispatchers.IO) {
                cartDao.update(cartProduct)
            }
        } else {
            launch(Dispatchers.IO) {
                cartDao.delete(cartProduct)
            }
        }
    }

    fun goToOrderClick() {
        consumerCartNavigator.get()?.goToOrder()
    }

}