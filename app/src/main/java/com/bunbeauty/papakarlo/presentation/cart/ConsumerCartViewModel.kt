package com.bunbeauty.papakarlo.presentation.cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.annotation.Api
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.toCreateOrder
import com.bunbeauty.presentation.item.CartProductItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor(
    @Api private val cartProductRepo: CartProductRepo,
    private val resourcesProvider: IResourcesProvider,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper
) : CartViewModel() {

    private val mutableOrderProductListState: MutableStateFlow<State<List<CartProductItem>>> =
        MutableStateFlow(State.Loading())
    val orderProductListState: StateFlow<State<List<CartProductItem>>> =
        mutableOrderProductListState.asStateFlow()

    private val mutableDeliveryInfo: MutableStateFlow<String> = MutableStateFlow("")
    val deliveryInfo: StateFlow<String> = mutableDeliveryInfo.asStateFlow()

    private val mutableOldTotalCost: MutableStateFlow<String> = MutableStateFlow("")
    val oldTotalCost: StateFlow<String> = mutableOldTotalCost.asStateFlow()

    init {
        subscribeOnCartProducts()
        subscribeOnDelivery()
    }

    fun onMenuClicked() {
        router.navigate(backToMenuFragment())
    }

    fun onCreateOrderClicked() {
        router.navigate(toCreateOrder())
    }

    private fun subscribeOnCartProducts() {
        cartProductRepo.observeCartProductList().onEach { productList ->
            mutableOrderProductListState.value = if (productList.isEmpty()) {
                State.Empty()
            } else {
                productList.map(::toItem).toStateSuccess()
            }
            val oldTotalCost = productHelper.getOldTotalCost(productList)
            mutableOldTotalCost.value = stringUtil.getCostString(oldTotalCost)
        }.launchIn(viewModelScope)
    }

    private fun subscribeOnDelivery() {
        dataStoreRepo.delivery.onEach { delivery ->
            mutableDeliveryInfo.value =
                resourcesProvider.getString(R.string.msg_consumer_cart_free_delivery_from) +
                        stringUtil.getCostString(delivery.forFree)
        }.launchIn(viewModelScope)
    }

    private fun toItem(cartProduct: CartProduct): CartProductItem {
        val newCost = productHelper.getProductPositionNewCost(cartProduct)
        val oldCost = productHelper.getProductPositionOldCost(cartProduct)
        val newCostString = stringUtil.getCostString(newCost)
        val oldCostString = stringUtil.getCostString(oldCost)

        return CartProductItem(
            uuid = cartProduct.uuid,
            name = cartProduct.menuProduct.name,
            newCost = newCostString,
            oldCost = oldCostString,
            photoLink = cartProduct.menuProduct.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.menuProduct.uuid
        )
    }
}