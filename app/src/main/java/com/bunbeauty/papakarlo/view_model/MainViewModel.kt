package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.live_data.ConnectionLiveData
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel @Inject constructor() : BaseViewModel() {

    var navigator: WeakReference<MainNavigator>? = null

    var isNetworkConnected = false

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            "${productList.sumBy { it.getFullPrice() }} ₽\n${productList.sumBy { it.count }} шт."
        }
    }
}