package com.bunbeauty.papakarlo.feature.consumercart.mapper

import com.bunbeauty.papakarlo.feature.consumercart.state.ConsumerCartViewState
import com.bunbeauty.papakarlo.feature.menu.mapper.toMenuProductItemUi
import com.bunbeauty.shared.presentation.consumercart.CartProductItem
import com.bunbeauty.shared.presentation.consumercart.ConsumerCart
import kotlinx.collections.immutable.toImmutableList

fun ConsumerCart.DataState.toConsumerCartViewState(): ConsumerCartViewState {
    return when (state) {
        ConsumerCart.DataState.State.LOADING -> {
            ConsumerCartViewState.Loading
        }

        ConsumerCart.DataState.State.SUCCESS -> {
            ConsumerCartViewState.Success(
                motivation = motivation?.let { warningItem ->
                    when (warningItem) {
                        is ConsumerCart.DataState.Motivation.MinOrderCost -> {
                            ConsumerCartViewState.MotivationUi.MinOrderCost(cost = warningItem.cost)
                        }

                        is ConsumerCart.DataState.Motivation.ForLowerDelivery -> {
                            ConsumerCartViewState.MotivationUi.ForLowerDelivery(
                                increaseAmountBy = warningItem.increaseAmountBy,
                                progress = warningItem.progress,
                                isFree = warningItem.isFree,
                            )
                        }

                        is ConsumerCart.DataState.Motivation.LowerDeliveryAchieved -> {
                            ConsumerCartViewState.MotivationUi.LowerDeliveryAchieved(isFree = warningItem.isFree)
                        }
                    }
                },
                cartProductList = cartProductItemList.mapIndexed { i, cartProductItem ->
                    cartProductItem.toCartProductItemUi(i == cartProductItemList.lastIndex)
                }.toImmutableList(),
                recommendationList = recommendationList.map { menuProduct ->
                    menuProduct.toMenuProductItemUi()
                }.toImmutableList(),
                discount = discount,
                oldTotalCost = oldTotalCost,
                newTotalCost = newTotalCost,
            )
        }

        ConsumerCart.DataState.State.EMPTY -> {
            ConsumerCartViewState.Empty(
                recommendationList = recommendationList.map { menuProduct ->
                    menuProduct.toMenuProductItemUi()
                }.toImmutableList()
            )
        }

        ConsumerCart.DataState.State.ERROR -> {
            ConsumerCartViewState.Error
        }
    }
}

private fun CartProductItem.toCartProductItemUi(isLast: Boolean): ConsumerCartViewState.CartProductItemUi {
    return ConsumerCartViewState.CartProductItemUi(
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
}