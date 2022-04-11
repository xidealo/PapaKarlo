package com.bunbeauty.papakarlo.feature.menu.category

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class CategoryDiffCallback : DefaultDiffCallback<CategoryItemModel>() {

    override fun getChangePayload(
        oldItem: CategoryItemModel,
        newItem: CategoryItemModel
    ): Any? {
        return if (oldItem.isSelected != newItem.isSelected)
            true
        else
            null
    }

}