package com.bunbeauty.papakarlo.feature.product_details

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState

class ProductDetailsUiStateMapper(
    private val stringUtil: IStringUtil
) {

    fun map(productDetailsState: ProductDetailsState): ProductDetailsUi {
        return ProductDetailsUi(
            topCartUi = TopCartUi(
                cost = productDetailsState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = productDetailsState.cartCostAndCount?.count ?: ""
            ),
            menuProductUi = productDetailsState.menuProduct?.let { menuProduct ->
                ProductDetailsUi.MenuProductUi(
                    photoLink = menuProduct.photoLink,
                    name = menuProduct.name,
                    size = menuProduct.size,
                    oldPrice = menuProduct.oldPrice?.let { oldPrice ->
                        stringUtil.getCostString(oldPrice)
                    },
                    newPrice = stringUtil.getCostString(menuProduct.newPrice),
                    description = menuProduct.description
                )
            }
        )
    }
}
