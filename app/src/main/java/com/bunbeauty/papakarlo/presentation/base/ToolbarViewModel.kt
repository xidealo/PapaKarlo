package com.bunbeauty.papakarlo.presentation.base

import com.bunbeauty.domain.util.product.IProductHelper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

abstract class ToolbarViewModel: BaseViewModel() {

    @Inject
    lateinit var productHelper: IProductHelper

    val cartFlow by lazy {
        cartProductListFlow.map { productList ->
            productHelper.getFullPriceString(productList) + "\n${productList.sumOf { it.count }} шт."
        }
    }
}