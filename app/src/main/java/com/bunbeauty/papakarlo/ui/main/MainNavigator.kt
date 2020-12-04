package com.bunbeauty.papakarlo.ui.main

import com.bunbeauty.papakarlo.data.model.MenuProduct

interface MainNavigator {
    fun goToConsumerCart(wishMenuProductList: ArrayList<MenuProduct>)
}