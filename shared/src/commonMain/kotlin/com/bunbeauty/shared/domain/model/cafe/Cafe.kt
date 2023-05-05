package com.bunbeauty.shared.domain.model.cafe

data class Cafe(
    val uuid: String,
    val fromTime: Int,
    val toTime: Int,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val cityUuid: String,
)
