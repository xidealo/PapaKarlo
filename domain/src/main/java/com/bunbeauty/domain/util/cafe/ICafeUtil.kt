package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.model.Cafe

interface ICafeUtil {
    fun getIsOpen(cafe: Cafe): Boolean
}