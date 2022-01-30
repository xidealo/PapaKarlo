package com.bunbeauty.papakarlo.feature.menu.category

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class CategoryDiffCallback : DefaultDiffCallback<CategoryItem>() {

    override fun getChangePayload(
        oldItem: CategoryItem,
        newItem: CategoryItem
    ): Any? {
        return if (oldItem.isSelected != newItem.isSelected)
            true
        else
            null
    }

}