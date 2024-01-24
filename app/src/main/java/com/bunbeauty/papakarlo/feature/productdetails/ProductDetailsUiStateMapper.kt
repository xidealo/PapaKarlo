package com.bunbeauty.papakarlo.feature.productdetails

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.presentation.product_details.AdditionItem
import com.bunbeauty.shared.presentation.product_details.MenuProductAdditionItem
import com.bunbeauty.shared.presentation.product_details.ProductDetailsState

class ProductDetailsUiStateMapper(
    private val stringUtil: IStringUtil
) {
    fun map(productDetailsState: ProductDetailsState.DataState): ProductDetailsViewState {
        val additionList = buildList {
            productDetailsState.menuProduct.additionGroups.forEach { additionGroup ->
                add(
                    AdditionItem.AdditionHeaderItem(
                        key = "AdditionHeaderItem + ${additionGroup.uuid}",
                        uuid = additionGroup.uuid,
                        name = additionGroup.name
                    )
                )
                addAll(
                    additionGroup.additionList.mapIndexed { index, addition ->
                        AdditionItem.AdditionListItem(
                            key = "AdditionListItem + ${addition.uuid}",
                            product = MenuProductAdditionItem(
                                uuid = addition.uuid,
                                isSelected = addition.isSelected,
                                name = addition.name,
                                price = addition.price?.let { price -> "+$price${Constants.RUBLE_CURRENCY}" },
                                isLast = additionGroup.additionList.lastIndex == index,
                                photoLink = addition.photoLink,
                                groupId = additionGroup.uuid
                            ),
                            isMultiple = !additionGroup.singleChoice
                        )
                    }
                )
            }
        }

        return when (productDetailsState.screenState) {
            ProductDetailsState.DataState.ScreenState.SUCCESS -> ProductDetailsViewState.Success(
                topCartUi = TopCartUi(
                    cost = productDetailsState.cartCostAndCount?.cost?.let { cost ->
                        stringUtil.getCostString(cost)
                    } ?: "",
                    count = productDetailsState.cartCostAndCount?.count ?: ""
                ),
                menuProductUi = productDetailsState.menuProduct.let { menuProduct ->
                    ProductDetailsViewState.Success.MenuProductUi(
                        photoLink = menuProduct.photoLink,
                        name = menuProduct.name,
                        size = menuProduct.size,
                        oldPrice = menuProduct.oldPrice?.let { oldPrice -> "$oldPrice${menuProduct.currency}" },
                        newPrice = "${menuProduct.newPrice}${menuProduct.currency}",
                        description = menuProduct.description,
                        additionList = additionList,
                        priceWithAdditions = "${menuProduct.priceWithAdditions}${menuProduct.currency}"
                    )
                }
            )

            ProductDetailsState.DataState.ScreenState.ERROR -> ProductDetailsViewState.Error
            ProductDetailsState.DataState.ScreenState.LOADING, ProductDetailsState.DataState.ScreenState.INIT -> ProductDetailsViewState.Loading
        }
    }
}
