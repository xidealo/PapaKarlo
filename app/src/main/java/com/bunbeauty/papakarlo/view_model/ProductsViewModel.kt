package com.bunbeauty.papakarlo.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductRepo
import com.bunbeauty.data.enums.ProductCode
import com.bunbeauty.data.model.MenuProduct
import com.bunbeauty.papakarlo.ui.menu.MenuFragmentDirections.toProductFragment
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val menuProductRepo: MenuProductRepo) :
    BaseViewModel() {

    val isLoadingLiveData = MutableLiveData(true)
    lateinit var productCode: ProductCode

    val productListLiveData by lazy {
        map(menuProductRepo.getMenuProductList(productCode)) { menuProductList ->
            isLoadingLiveData.value = false
            menuProductList.sortedBy { it.name }.filter { it.visible }
        }
    }

    fun onProductClicked(menuProduct: MenuProduct) {
        router.navigate(toProductFragment(menuProduct, menuProduct.name))
    }
}