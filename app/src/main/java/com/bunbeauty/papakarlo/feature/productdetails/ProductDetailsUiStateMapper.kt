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
    fun map(productDetailsState: ProductDetailsState.ViewDataState): ProductDetailsUi {
        // для PR вынес потому что в вью модели нужны изменяемые данные, в свифте буду использовать свои модели со своими ключами
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
                        if (additionGroup.singleChoice) {
                            AdditionItem.AdditionSingleListItem(
                                key = "AdditionSingleListItem + ${addition.uuid}",
                                product = MenuProductAdditionItem(
                                    uuid = addition.uuid,
                                    isSelected = addition.isSelected,
                                    name = addition.name,
                                    price = addition.price?.let { price -> "+$price${Constants.RUBLE_CURRENCY}" },
                                    isLast = additionGroup.additionList.lastIndex == index,
                                    photoLink = addition.photoLink,
                                    groupId = additionGroup.uuid
                                )
                            )
                        } else {
                            AdditionItem.AdditionMultiplyListItem(
                                key = "AdditionMultiplyListItem + ${addition.uuid}",
                                product = MenuProductAdditionItem(
                                    uuid = addition.uuid,
                                    isSelected = addition.isSelected,
                                    name = addition.name,
                                    price = addition.price?.let { price -> "+$price${Constants.RUBLE_CURRENCY}" },
                                    isLast = additionGroup.additionList.lastIndex == index,
                                    photoLink = addition.photoLink,
                                    groupId = additionGroup.uuid
                                )
                            )
                        }
                    }
                )
            }
        }

        return when (productDetailsState.screenState) {
            ProductDetailsState.ViewDataState.ScreenState.SUCCESS -> ProductDetailsUi.Success(
                topCartUi = TopCartUi(
                    cost = productDetailsState.cartCostAndCount?.cost?.let { cost ->
                        stringUtil.getCostString(cost)
                    } ?: "",
                    count = productDetailsState.cartCostAndCount?.count ?: ""
                ),
                menuProductUi = productDetailsState.menuProduct.let { menuProduct ->
                    ProductDetailsUi.Success.MenuProductUi(
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

            ProductDetailsState.ViewDataState.ScreenState.ERROR -> ProductDetailsUi.Error
            ProductDetailsState.ViewDataState.ScreenState.LOADING, ProductDetailsState.ViewDataState.ScreenState.INIT -> ProductDetailsUi.Loading
        }
    }
}
