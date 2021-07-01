package com.bunbeauty.papakarlo.presentation

import android.util.Log
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.asLiveData
import com.bunbeauty.data.mapper.adapter.CartProductAdapterMapper
import com.bunbeauty.domain.model.adapter.CartProductAdapterModel
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringHelper
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
    private val dataStoreRepo: DataStoreRepo,
    private val stringHelper: IStringHelper,
    private val resourcesProvider: IResourcesProvider,
    private val cartProductAdapterMapper: CartProductAdapterMapper
) : ToolbarViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job()

    val deliveryStringLiveData by lazy {
        switchMap(dataStoreRepo.delivery.asLiveData()) { delivery ->
            map(cartProductRepo.getCartProductListFlow().asLiveData()) { productList ->
                val differenceString = productHelper.getDifferenceBeforeFreeDeliveryString(
                    productList,
                    delivery.forFree
                )
                Log.d("test", "delivery " + delivery.cost)
                if (differenceString.isEmpty()) {
                    resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery)
                } else {
                    resourcesProvider.getString(R.string.part_consumer_cart_free_delivery_from) +
                            stringHelper.getCostString(delivery.forFree) +
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

    fun getCartProductModel(cartProductList:List<CartProduct>): List<CartProductAdapterModel>{
        return cartProductList.map { cartProductAdapterMapper.from(it) }
    }

    fun onMenuClicked() {
        router.navigate(ConsumerCartFragmentDirections.backToMenuFragment())
    }

    fun onCreateOrderClicked() {
        router.navigate(ConsumerCartFragmentDirections.toCreationOrder())
    }

}