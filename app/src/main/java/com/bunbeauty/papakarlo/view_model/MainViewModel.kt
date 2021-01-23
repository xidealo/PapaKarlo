package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.live_data.ConnectionLiveData
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

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