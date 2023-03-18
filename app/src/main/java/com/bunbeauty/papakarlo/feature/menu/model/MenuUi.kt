package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi

data class MenuUi(
    val categoryItemList: List<CategoryItem> = emptyList(),
    val topCartUi: TopCartUi,
    val menuItemList: List<MenuItem> = emptyList(),
    val state: MenuState.State = MenuState.State.Loading,
    val eventList: List<MenuState.Event> = emptyList(),
)
