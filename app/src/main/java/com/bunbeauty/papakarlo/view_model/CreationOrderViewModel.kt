package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductDao
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.order.OrderWithCartProducts
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class CreationOrderViewModel @Inject constructor(
    private val orderRepo: OrderRepo,
    private val cartProductDao: CartProductDao
) : BaseViewModel() {

    lateinit var creationOrderNavigator: WeakReference<CreationOrderNavigator>

    fun createOrder(orderWithCartProducts: OrderWithCartProducts) {
        viewModelScope.launch {
            val order = orderRepo.insertOrderAsync(orderWithCartProducts.order).await()

            for (cartProduct in orderWithCartProducts.cartProducts) {
                cartProduct.orderUuid = order.uuid
                cartProductDao.insert(cartProduct)
            }
        }
    }

    fun createOrderClick() {
        creationOrderNavigator.get()?.createOrder()
    }
}