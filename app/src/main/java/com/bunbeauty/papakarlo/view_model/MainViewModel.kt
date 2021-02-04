package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.utils.live_data.ConnectionLiveData
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cafeRepo: CafeRepo
    ) : BaseViewModel() {

    var navigator: WeakReference<MainNavigator>? = null

    var isNetworkConnected = false

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    @Inject
    lateinit var dataStoreHelper: IDataStoreHelper

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            "${productList.sumBy { it.fullPrice() }} ₽\n${productList.sumBy { it.count }} шт."
        }
    }

    fun refreshCafeList() {
        viewModelScope.launch {
            cafeRepo.refreshCafeList()
        }
    }
}