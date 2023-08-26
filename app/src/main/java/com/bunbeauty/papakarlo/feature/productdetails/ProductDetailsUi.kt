package com.bunbeauty.papakarlo.feature.productdetails

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi

data class ProductDetailsUi(
    val topCartUi: TopCartUi,
    val menuProductUi: MenuProductUi?
) {

    data class MenuProductUi(
        val photoLink: String,
        val name: String,
        val size: String,
        val oldPrice: String?,
        val newPrice: String,
        val description: String
    )
}
