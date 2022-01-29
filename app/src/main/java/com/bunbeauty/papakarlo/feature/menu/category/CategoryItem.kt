package com.bunbeauty.papakarlo.feature.menu.category

import com.bunbeauty.papakarlo.common.BaseItem

data class CategoryItem(
    override var uuid: String,
    val name: String,
    val isSelected: Boolean
) : BaseItem()