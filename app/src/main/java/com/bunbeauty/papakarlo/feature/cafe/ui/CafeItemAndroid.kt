package com.bunbeauty.papakarlo.feature.cafe.ui

import com.bunbeauty.shared.presentation.cafe_list.CafeItem

data class CafeItemAndroid(
    val uuid: String,
    val address: String,
    val phone: String,
    val workingHours: String,
    val cafeStatusText: String,
    val cafeOpenState: CafeItem.CafeOpenState,
)
