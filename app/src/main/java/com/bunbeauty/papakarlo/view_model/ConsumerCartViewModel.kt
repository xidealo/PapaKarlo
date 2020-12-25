package com.bunbeauty.papakarlo.view_model

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