package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil

class MenuUiStateMapper(
    private val stringUtil: IStringUtil,
) {
    fun map(menuState: MenuState): MenuUi {
        return MenuUi(
            topCartUi = TopCartUi(
                cost = menuState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = menuState.cartCostAndCount?.count ?: ""
            ),
            categoryItemList = menuState.categoryItemList,
            menuItemList = menuState.menuItemList,
            state = menuState.state,
            eventList = menuState.eventList,
        )
    }
}