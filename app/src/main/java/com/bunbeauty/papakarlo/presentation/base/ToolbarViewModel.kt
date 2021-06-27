package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.Transformations
import com.bunbeauty.domain.util.product.IProductHelper
import javax.inject.Inject

abstract class ToolbarViewModel: BaseViewModel() {

    @Inject
    lateinit var productHelper: IProductHelper

    val cartLiveData by lazy {
        Transformations.map(cartProductListLiveData) { productList ->
            productHelper.getFullPriceString(productList) + "\n${productList.sumOf { it.count }} шт."
        }
    }
}