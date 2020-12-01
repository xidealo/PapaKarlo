package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class ProductsViewModel @Inject constructor() : BaseViewModel() {

    val productsField = ObservableField<List<Product>?>()
    val isLoading = ObservableField(true)

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    var productList = mutableListOf<Product>()

    fun getProducts(productCode: ProductCode) {
        productsField.set(
            listOf(
                Product(name = "Pizza", productCode = ProductCode.Pizza),
                Product(name = "Hamburger", productCode = ProductCode.Hamburger),
            )
        )
    }

}