package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.data.model.Order
import com.bunbeauty.papakarlo.ui.creation_order.CreationOrderNavigator
import com.bunbeauty.papakarlo.ui.product.ProductNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class OrderViewModel @Inject constructor(private val orderRepo: OrderRepo) : BaseViewModel() {

    lateinit var creationOrderNavigator: WeakReference<CreationOrderNavigator>

    fun createOrder(order: Order) {
        viewModelScope.launch {
            orderRepo.insertOrder(order)
        }
    }

    fun createOrderClick() {
        creationOrderNavigator.get()?.createOrder()
    }
}