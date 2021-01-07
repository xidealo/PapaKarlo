package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.enums.ProductCode
import com.bunbeauty.papakarlo.ui.products.ProductsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) : BaseViewModel() {

    var navigator: WeakReference<ProductsNavigator>? = null
    val isLoading = ObservableField(true)
    lateinit var productCode: ProductCode

    val productListLiveData by lazy {
        menuProductRepo.getMenuProductList(productCode).also {
            isLoading.set(false)
        }
    }

    fun goToProduct(menuProduct: MenuProduct) {
        navigator?.get()?.goToProduct(menuProduct)
    }
}