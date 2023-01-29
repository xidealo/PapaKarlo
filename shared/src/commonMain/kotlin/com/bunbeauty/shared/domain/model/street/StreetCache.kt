package com.bunbeauty.shared.domain.model.street

data class StreetCache(
    val streetList: List<Street>,
    val userUuid: String,
    val cityUuid: String,
)
