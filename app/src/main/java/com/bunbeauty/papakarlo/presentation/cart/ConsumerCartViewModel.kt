package com.bunbeauty.papakarlo.presentation.cart

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateSuccess
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.backToMenuFragment
import com.bunbeauty.papakarlo.ui.ConsumerCartFragmentDirections.toCreationOrder
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ConsumerCartViewModel @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    private val resourcesProvider: IResourcesProvider,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

    private val mutableCartProductListState: MutableStateFlow<State<List<CartProductItem>>> =
        MutableStateFlow(State.Loading())
    val cartProductListState: StateFlow<State<List<CartProductItem>>> =
        mutableCartProductListState.asStateFlow()

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
        router.navigate(toCreationOrder())
    }

    private fun subscribeOnCartProducts() {
        cartProductRepo.cartProductList.onEach { productList ->
            mutableCartProductListState.value = if (productList.isEmpty()) {
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
                        delivery.forFree
        }.launchIn(viewModelScope)
    }

    private fun toItem(cartProduct: CartProduct): CartProductItem {
        val newCost = productHelper.getCartProductNewCost(cartProduct)
        val oldCost = productHelper.getCartProductOldCost(cartProduct)
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