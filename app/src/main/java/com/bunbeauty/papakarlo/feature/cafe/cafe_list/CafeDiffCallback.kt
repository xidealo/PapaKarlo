package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import com.bunbeauty.papakarlo.common.DefaultDiffCallback

class CafeDiffCallback : DefaultDiffCallback<CafeItem>() {

    override fun getChangePayload(oldItem: CafeItem, newItem: CafeItem): Any? {
        return if (oldItem.isOpenMessage != newItem.isOpenMessage)
            true
        else
            null
    }

}