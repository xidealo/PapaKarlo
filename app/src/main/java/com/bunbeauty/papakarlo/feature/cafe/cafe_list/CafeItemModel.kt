package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import com.bunbeauty.domain.model.cafe.CafeStatus

data class CafeItemModel(
    val uuid: String,
    val address: String,
    val workingHours: String,
    val isOpenMessage: String,
    val cafeStatus: CafeStatus,
)