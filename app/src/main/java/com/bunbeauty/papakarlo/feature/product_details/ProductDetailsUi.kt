package com.bunbeauty.papakarlo.feature.product_details

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi

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