package com.bunbeauty.papakarlo.presentation.profile

import androidx.lifecycle.viewModelScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.toStateNullableSuccess
import com.bunbeauty.presentation.view_model.base.adapter.CartProductAdapterModel
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.Delivery
import com.bunbeauty.domain.model.local.order.Order
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val dataStoreRepo: DataStoreRepo,
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

    fun getCartProductModel(cartProductList: List<CartProduct>) = cartProductList.map(::toItemModel)

    private fun toItemModel(cartProduct: CartProduct): CartProductAdapterModel {
        return CartProductAdapterModel(
            uuid = cartProduct.uuid,
            name = cartProduct.menuProduct.name,
            cost = productHelper.getCartProductPriceString(cartProduct),
            discountCost = productHelper.getCartProductOldPriceString(cartProduct),
            photoLink = cartProduct.menuProduct.photoLink,
            count = cartProduct.count
        )
    }
}