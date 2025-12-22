package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.shared.domain.model.cafe.CafeOpenState

data class CafeItem(
    val uuid: String,
    val address: String,
    val phone: String,
    val workingHours: String,
    val cafeOpenState: CafeOpenState,
    val isLast: Boolean,
)
