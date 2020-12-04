package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    lateinit var mainNavigator: WeakReference<MainNavigator>

    val productsLiveData: MutableLiveData<ArrayList<MenuProduct>> by lazy {
        MutableLiveData<ArrayList<MenuProduct>>()
    }
    val isLoading = ObservableField(true)

    var productList = arrayListOf(
        MenuProduct(name = "Pizza", productCode = ProductCode.Pizza, cost = 10),
        MenuProduct(name = "Pizza 312", productCode = ProductCode.Pizza, cost = 23),
        MenuProduct(name = "Hamburger", productCode = ProductCode.Hamburger, cost = 11),
        MenuProduct(name = "Hamburger 2", productCode = ProductCode.Hamburger, cost = 65),
    )

    var wishProductList = arrayListOf<MenuProduct>()

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