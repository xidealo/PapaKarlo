package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import com.bunbeauty.domain.model.cafe.CafeStatus
import com.bunbeauty.papakarlo.common.BaseItem

data class CafeItemModel(
    override val uuid: String,
    val address: String,
    val workingHours: String,
    val isOpenMessage: String,
    val cafeStatus: CafeStatus,
) : BaseItem()