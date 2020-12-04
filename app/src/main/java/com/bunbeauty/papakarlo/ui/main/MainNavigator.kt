package com.bunbeauty.papakarlo.ui.main

import com.bunbeauty.papakarlo.data.model.CartProduct

interface MainNavigator {
    fun goToConsumerCart(wishMenuProductList: Set<CartProduct>)
}