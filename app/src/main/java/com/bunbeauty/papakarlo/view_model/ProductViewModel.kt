package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.model.Product
import com.bunbeauty.papakarlo.ui.ProductNavigator
import com.bunbeauty.papakarlo.ui.main.MainNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductViewModel @Inject constructor() : BaseViewModel() {

    lateinit var productNavigator: WeakReference<ProductNavigator>

    fun addWishProductClick(product: Product) {
        productNavigator.get()?.addWishProduct(product)
    }

}