package com.bunbeauty.papakarlo.view_model.base

import androidx.lifecycle.Transformations
import com.bunbeauty.papakarlo.utils.product.IProductHelper
import javax.inject.Inject

abstract class ToolbarViewModel: BaseViewModel() {

    @Inject
    lateinit var productHelper: IProductHelper

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            productHelper.getFullPriceString(productList) + "\n${productList.sumBy { it.count }} шт."
        }
    }
}