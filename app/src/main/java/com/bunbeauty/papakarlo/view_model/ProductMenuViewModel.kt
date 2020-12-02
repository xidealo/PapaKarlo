package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductMenuViewModel @Inject constructor() : BaseViewModel() {

    val productsLiveData: MutableLiveData<ArrayList<Product>> by lazy {
        MutableLiveData<ArrayList<Product>>()
    }
    val isLoading = ObservableField(true)

    var productList = arrayListOf(
        Product(name = "Pizza", productCode = ProductCode.Pizza, photoLink = "https://eda.ru/img/eda/1200x-i/s2.eda.ru/StaticContent/Photos/120131085053/171027192707/p_O.jpg"),
        Product(name = "Pizza 312", productCode = ProductCode.Pizza),
        Product(name = "Hamburger", productCode = ProductCode.Hamburger),
        Product(name = "Hamburger 2", productCode = ProductCode.Hamburger),
    )

    fun getProducts() {
        viewModelScope.launch {
            productsLiveData.value = productList
            isLoading.set(false)
        }
    }
}