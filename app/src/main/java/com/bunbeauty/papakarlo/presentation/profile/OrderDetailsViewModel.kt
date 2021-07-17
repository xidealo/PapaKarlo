package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.presentation.view_model.base.adapter.CartProductItem
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val stringUtil: IStringUtil,
    private val productHelper: IProductHelper
) : BaseViewModel() {

    private val _orderState: MutableStateFlow<State<Order?>> = MutableStateFlow(State.Loading())
    val orderState: StateFlow<State<Order?>>
        get() = _orderState.asStateFlow()

    val delivery: Delivery
        get() = runBlocking {
            dataStoreRepo.delivery.first()
        }

    fun getOrder(orderUuid: String) {
        orderRepo.getOrderWithCartProducts(orderUuid).onEach { order ->
            if (order != null)
                _orderState.value = order.toStateNullableSuccess()
            else
                _orderState.value = State.Empty()
        }.launchIn(viewModelScope)
    }

    fun getCartProductModel(cartProductList: List<CartProduct>) = cartProductList.map(::toItem)

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