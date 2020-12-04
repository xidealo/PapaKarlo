package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

class ProductsViewModel @Inject constructor() : BaseViewModel() {

    val productsField = ObservableField<List<MenuProduct>?>()

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun filterProducts(productCode: ProductCode, menuProducts: ArrayList<MenuProduct>) {
        if (productCode == ProductCode.All) {
            productsField.set(
                menuProducts
            )
            return
        }

        productsField.set(
            menuProducts.filter { it.productCode == productCode }
        )
    }

}