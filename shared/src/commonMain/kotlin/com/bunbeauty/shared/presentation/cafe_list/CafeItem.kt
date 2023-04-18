package com.bunbeauty.shared.presentation.cafe_list

data class CafeItem(
    val uuid: String,
    val address: String,
    val workingHours: String,
    val cafeOpenState: CafeOpenState,
) {
    sealed class CafeOpenState {
        object Opened : CafeOpenState()
        object Closed : CafeOpenState()
        data class CloseSoon(val time: Int) : CafeOpenState()
    }
}
