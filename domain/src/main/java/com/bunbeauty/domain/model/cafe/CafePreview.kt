package com.bunbeauty.domain.model.cafe

data class CafePreview(
    val uuid: String,
    val fromTime: String,
    val toTime: String,
    val address: String,
    val isOpen: Boolean,
    val closeIn: Int?,
)
