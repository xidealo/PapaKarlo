package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations.map
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.utils.product.IProductHelper
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConsumerCartViewModel @Inject constructor(private val productHelper: IProductHelper, private val resourcesProvider: IResourcesProvider) : BaseViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    val orderStringLiveData by lazy {
        map(cartProductRepo.getCartProductListLiveData()) { productList ->
            resourcesProvider.getString(R.string.action_creation_order_checkout) +
                    productHelper.getFullPriceString(productList)
        }
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