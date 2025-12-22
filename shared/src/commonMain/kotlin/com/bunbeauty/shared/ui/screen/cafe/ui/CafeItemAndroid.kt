package com.bunbeauty.shared.ui.screen.cafe.ui

import com.bunbeauty.shared.domain.model.cafe.CafeOpenState

data class CafeItemAndroid(
    val uuid: String,
    val address: String,
    val phone: String,
    val workingHours: String,
    val cafeStatusText: String,
    val cafeOpenState: CafeOpenState,
    val isLast: Boolean,
)
