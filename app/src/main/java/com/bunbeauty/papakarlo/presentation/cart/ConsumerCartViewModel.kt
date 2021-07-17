package com.bunbeauty.papakarlo.presentation.cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.data.mapper.adapter.CartProductAdapterMapper
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val stringHelper: IStringHelper,
    private val cartProductRepo: CartProductRepo,
    private val productHelper: IProductHelper,
    private val resourcesProvider: IResourcesProvider,
    private val cartProductAdapterMapper: CartProductAdapterMapper
) : BaseViewModel() {

    val cartProductList = cartProductRepo.getCartProductListFlow()
        .map { productList ->
            productList.sortedBy { cartProduct ->
                cartProduct.menuProduct.name
            }.map { cartProduct ->
                cartProductAdapterMapper.from(cartProduct)
            }
        }

    val deliveryStringFlow by lazy {
        dataStoreRepo.delivery.flatMapLatest { delivery ->
            cartProductRepo.getCartProductListFlow().map { productList ->
                val differenceString = productHelper.getDifferenceBeforeFreeDeliveryString(
                    productList,
                    delivery.forFree
                )
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

    fun updateCartProduct(cartProductUuid: String, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (count > 0) {
                cartProductRepo.update(
                    cartProductRepo.getCartProduct(cartProductUuid).also { it?.count = count }
                        ?: return@launch)
            } else {
                val cartProduct = cartProductRepo.getCartProduct(cartProductUuid) ?: return@launch
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