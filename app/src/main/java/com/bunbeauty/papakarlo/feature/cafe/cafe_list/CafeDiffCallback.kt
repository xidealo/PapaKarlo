package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class CafeDiffCallback : DefaultDiffCallback<CafeItemModel>() {

    override fun getChangePayload(oldItem: CafeItemModel, newItem: CafeItemModel): Any? {
        return if (oldItem.isOpenMessage != newItem.isOpenMessage)
            true
        else
            null
    }

}