package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    lateinit var mainNavigator: WeakReference<MainNavigator>

    val productsLiveData: MutableLiveData<ArrayList<Product>> by lazy {
        MutableLiveData<ArrayList<Product>>()
    }
    val isLoading = ObservableField(true)

    var productList = arrayListOf(
        Product(name = "Pizza", productCode = ProductCode.Pizza, cost = 10),
        Product(name = "Pizza 312", productCode = ProductCode.Pizza, cost = 23),
        Product(name = "Hamburger", productCode = ProductCode.Hamburger, cost = 11),
        Product(name = "Hamburger 2", productCode = ProductCode.Hamburger, cost = 65),
    )

    var wishProductList = arrayListOf<Product>()

    fun getProducts() {
        viewModelScope.launch {
            productsLiveData.value = productList
            isLoading.set(false)
        }
    }

    fun goToConsumerCartClick() {
        mainNavigator.get()?.goToConsumerCart(wishProductList)
    }
}