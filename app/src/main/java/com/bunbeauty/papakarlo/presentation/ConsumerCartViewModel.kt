package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import com.bunbeauty.data.model.CartProduct
import com.bunbeauty.common.utils.IDataStoreHelper
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConsumerCartViewModel @Inject constructor(
    private val dataStoreHelper: IDataStoreHelper,
    private val stringHelper: IStringHelper,
    private val resourcesProvider: IResourcesProvider
) : ToolbarViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    /*val orderStringLiveData by lazy {
        map(cartProductRepo.getCartProductListLiveData()) { productList ->
            resourcesProvider.getString(R.string.action_creation_order_checkout) +
                    productHelper.getFullPriceString(productList)
        }
    }*/

    val deliveryStringLiveData by lazy {
        switchMap(dataStoreHelper.delivery.asLiveData()) { delivery ->
           map(cartProductRepo.getCartProductListLiveData()) { productList ->
                val differenceString = productHelper.getDifferenceBeforeFreeDeliveryString(
                    productList,
                    delivery.forFree
                )
                if (differenceString.isEmpty()) {
                    resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery)
                } else {
                    resourcesProvider.getString(R.string.part_consumer_cart_free_delivery_from) +
                            stringHelper.toStringPrice(delivery.forFree) +
                            resourcesProvider.getString(R.string.part_consumer_cart_difference_before_free_delivery) +
                            differenceString
                }
            }
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

    fun onMenuClicked() {
        router.navigate(ConsumerCartFragmentDirections.backToMenuFragment())
    }

    fun onCreateOrderClicked() {
        router.navigate(ConsumerCartFragmentDirections.toCreationOrder())
    }

}