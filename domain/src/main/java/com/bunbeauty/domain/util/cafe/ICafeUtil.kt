package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.model.Cafe

interface ICafeUtil {
    fun getIsClosedColorId(cafe: Cafe): Int
}