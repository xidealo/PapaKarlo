package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.menu.CategoryItem
import com.bunbeauty.shared.presentation.menu.MenuItem
import com.bunbeauty.shared.presentation.menu.MenuState

data class MenuUi(
    val categoryItemList: List<CategoryItem> = emptyList(),
    val topCartUi: TopCartUi,
    val menuItemList: List<MenuItem> = emptyList(),
    val discount: String?,
    val state: MenuState.State = MenuState.State.Loading,
    val eventList: List<MenuState.Event> = emptyList()
)
