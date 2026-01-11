package com.bunbeauty.core.model.street

data class StreetCache(
    val streetList: List<Street>,
    val userUuid: String,
    val cityUuid: String,
)
