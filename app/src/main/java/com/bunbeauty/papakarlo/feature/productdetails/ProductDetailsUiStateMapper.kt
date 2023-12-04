package com.bunbeauty.papakarlo.feature.productdetails

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState

class ProductDetailsUiStateMapper(
    private val stringUtil: IStringUtil,
) {
    fun map(productDetailsState: ProductDetailsState.ViewDataState): ProductDetailsUi {
        return when (productDetailsState.screenState) {
            ProductDetailsState.ViewDataState.ScreenState.SUCCESS -> ProductDetailsUi.Success(
                topCartUi = TopCartUi(
                    cost = productDetailsState.cartCostAndCount?.cost?.let { cost ->
                        stringUtil.getCostString(cost)
                    } ?: "",
                    count = productDetailsState.cartCostAndCount?.count ?: ""
                ),
                menuProductUi = productDetailsState.menuProduct?.let { menuProduct ->
                    ProductDetailsUi.Success.MenuProductUi(
                        photoLink = menuProduct.photoLink,
                        name = menuProduct.name,
                        size = menuProduct.size,
                        oldPrice = menuProduct.oldPrice?.let { oldPrice ->
                            stringUtil.getCostString(oldPrice)
                        },
                        newPrice = stringUtil.getCostString(menuProduct.newPrice),
                        description = menuProduct.description,
                        additionList = menuProduct.additionList
                    )
                }
            )

            ProductDetailsState.ViewDataState.ScreenState.ERROR -> ProductDetailsUi.Error
            ProductDetailsState.ViewDataState.ScreenState.LOADING -> ProductDetailsUi.Loading
        }
    }
}
