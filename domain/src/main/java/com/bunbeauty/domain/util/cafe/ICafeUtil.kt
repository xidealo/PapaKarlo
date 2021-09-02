package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.model.ui.Cafe

interface ICafeUtil {
    fun getIsClosedColor(cafe: Cafe): Int
}