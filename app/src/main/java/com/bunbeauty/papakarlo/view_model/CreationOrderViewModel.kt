package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.order.Order
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(private val orderRepo: OrderRepo) :
    BaseViewModel<CreationOrderNavigator>() {

    override var navigator: WeakReference<CreationOrderNavigator>? = null

    fun createOrder(order: Order) {
        viewModelScope.launch {
            val orderWithCartProducts = OrderWithCartProducts(
                order,
                cartProductRepo.getCartProductListAsync().await()
            )

            val insertedOrder = orderRepo.insertOrderAsync(orderWithCartProducts.order).await()

            for (cartProduct in orderWithCartProducts.cartProducts) {
                cartProduct.orderUuid = insertedOrder.uuid
                cartProductRepo.insertAsync(cartProduct)
            }

            navigator?.get()?.goToMain(order)
        }
    }
}