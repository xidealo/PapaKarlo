package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.menu.MenuState

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
            eventList = menuState.eventList
        )
    }
}
