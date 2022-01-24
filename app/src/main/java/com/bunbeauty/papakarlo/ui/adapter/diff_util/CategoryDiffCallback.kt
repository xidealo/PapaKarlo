package com.bunbeauty.papakarlo.ui.adapter.diff_util

import com.bunbeauty.presentation.item.CategoryItem

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