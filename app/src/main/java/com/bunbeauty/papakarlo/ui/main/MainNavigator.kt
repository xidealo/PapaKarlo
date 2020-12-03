package com.bunbeauty.papakarlo.ui.main

import com.bunbeauty.papakarlo.data.model.Product

interface MainNavigator {
    fun goToConsumerCart(wishProductList: ArrayList<Product>)
}