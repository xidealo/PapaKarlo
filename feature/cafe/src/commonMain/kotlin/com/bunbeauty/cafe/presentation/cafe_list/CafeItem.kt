package com.bunbeauty.cafe.presentation.cafe_list

import com.bunbeauty.core.model.cafe.CafeOpenState

data class CafeItem(
    val uuid: String,
    val address: String,
    val phone: String,
    val workingHours: String,
    val cafeOpenState: CafeOpenState,
    val isLast: Boolean,
)
