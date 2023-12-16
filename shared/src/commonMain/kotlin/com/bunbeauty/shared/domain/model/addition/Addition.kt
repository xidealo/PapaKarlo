package com.bunbeauty.shared.domain.model.addition

data class Addition(
    val isSelected: Boolean,
    val isVisible: Boolean,
    val name: String,
    val photoLink: String,
    val price: Int?,
    val uuid: String,
    val menuProductUuid: String,
)