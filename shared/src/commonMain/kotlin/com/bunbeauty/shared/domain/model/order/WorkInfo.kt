package com.bunbeauty.shared.domain.model.order

data class WorkInfo(
    val workInfoType: WorkInfoType
) {
    enum class WorkInfoType {
        DELIVERY,
        PICKUP,
        DELIVERY_AND_PICKUP,
        CLOSED
    }
}
