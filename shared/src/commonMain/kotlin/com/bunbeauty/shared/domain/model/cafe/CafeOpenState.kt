package com.bunbeauty.shared.domain.model.cafe

sealed class CafeOpenState {
    data object Opened : CafeOpenState()
    data object Closed : CafeOpenState()
    data class CloseSoon(val minutesUntil: Int) : CafeOpenState()
}