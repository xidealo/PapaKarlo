package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.model.local.cafe.Coordinate

data class CafeOptionModel(
    val address: String,
    val phone: String,
    val phoneWithText: String,
    val coordinate: Coordinate
)