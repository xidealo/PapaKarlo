package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class ProductsViewModel @Inject constructor() : BaseViewModel() {

    val productsField = ObservableField<List<Product>?>()

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun filterProducts(productCode: ProductCode, products: ArrayList<Product>) {
        if (productCode == ProductCode.All) {
            productsField.set(
                products
            )
            return
        }

        productsField.set(
            products.filter { it.productCode == productCode }
        )
    }

}