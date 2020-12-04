package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.ui.ProductNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductViewModel @Inject constructor() : BaseViewModel() {

    lateinit var productNavigator: WeakReference<ProductNavigator>

    fun addWishProductClick(menuProduct: MenuProduct) {
        productNavigator.get()?.addWishProduct(menuProduct)
    }

}