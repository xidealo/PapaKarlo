package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.local.db.order.OrderRepo
import com.bunbeauty.papakarlo.ui.base.BaseNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class OrdersViewModel @Inject constructor(private val orderRepo: OrderRepo) : BaseViewModel<BaseNavigator>() {

    override var navigator: WeakReference<BaseNavigator>? = null

    val orderWithCartProductsLiveData by lazy {
        orderRepo.getOrdersWithCartProducts()
    }
}