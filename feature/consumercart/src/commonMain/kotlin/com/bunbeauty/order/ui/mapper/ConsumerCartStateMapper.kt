package com.bunbeauty.order.ui.mapper

import com.bunbeauty.core.motivation.toMotivationUi
import com.bunbeauty.order.presentation.consumercart.CartProductItem
import com.bunbeauty.order.presentation.consumercart.ConsumerCart
import com.bunbeauty.order.ui.state.ConsumerCartViewState
import kotlinx.collections.immutable.toImmutableList

fun ConsumerCart.DataState.toConsumerCartViewState(): ConsumerCartViewState =
    when (state) {
        ConsumerCart.DataState.State.LOADING -> {
            ConsumerCartViewState.Loading
        }

        ConsumerCart.DataState.State.SUCCESS -> {
            ConsumerCartViewState.Success(
                cartProductList =
                    cartProductItemList
                        .mapIndexed { i, cartProductItem ->
                            cartProductItem.toCartProductItemUi(isLast = i == cartProductItemList.lastIndex)
                        }.toImmutableList(),
                recommendationList =
                    recommendationList
                        .map { menuProduct ->
                            ConsumerCartViewState.Success.ProductUi(
                                key = "MenuProductItem ${menuProduct.uuid}",
                                uuid = menuProduct.uuid,
                                photoLink = menuProduct.photoLink,
                                name = menuProduct.name,
                                oldPrice = menuProduct.oldPrice,
                                newPrice = menuProduct.newPrice,
                            )
                        }.toImmutableList(),
                bottomPanelInfo =
                    if (cartProductItemList.isEmpty()) {
                        null
                    } else {
                        ConsumerCartViewState.BottomPanelInfoUi(
                            motivation = motivation?.toMotivationUi(),
                            discount = discount,
                            oldTotalCost = oldTotalCost,
                            newTotalCost = newTotalCost,
                            orderAvailable = orderAvailable,
                        )
                    },
            )
        }

        ConsumerCart.DataState.State.ERROR -> {
            ConsumerCartViewState.Error
        }
    }

private fun CartProductItem.toCartProductItemUi(isLast: Boolean): ConsumerCartViewState.CartProductItemUi =
    ConsumerCartViewState.CartProductItemUi(
        key = "CartProductItem $uuid",
        uuid = uuid,
        name = name,
        newCost = newCost,
        oldCost = oldCost,
        photoLink = photoLink,
        count = count,
        additions = additions,
        isLast = isLast,
    )
