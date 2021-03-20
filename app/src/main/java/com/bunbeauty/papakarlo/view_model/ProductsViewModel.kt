package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.papakarlo.ui.products.ProductsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) :
    BaseViewModel() {

    var navigator: WeakReference<ProductsNavigator>? = null
    val isLoadingLiveData = MutableLiveData(true)
    lateinit var productCode: ProductCode

    val productListLiveData by lazy {
        map(menuProductRepo.getMenuProductList(productCode)) { menuProductList ->
            isLoadingLiveData.value = false
            menuProductList.sortedBy { it.name }.filter { it.visible }
        }
    }

    fun goToProduct(menuProduct: com.bunbeauty.data.model.MenuProduct) {
        navigator?.get()?.goToProduct(menuProduct)
    }
}