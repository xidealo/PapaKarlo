package com.bunbeauty.shared.ui.screen.consumercart.mapper

import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart
import com.bunbeauty.shared.ui.screen.consumercart.state.ConsumerCartViewState
import com.bunbeauty.shared.ui.screen.menu.mapper.toMenuProductItemUi
import com.bunbeauty.shared.ui.screen.motivation.toMotivationUi
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
                            menuProduct.toMenuProductItemUi()
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
