package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.cafe.CafeWithDistricts
import com.bunbeauty.domain.model.firebase.cafe.CafeFirebase
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.model.ui.address.CafeAddress

interface ICafeMapper {

    fun toUIModel(cafeEntity: CafeEntity): Cafe
    fun toEntityModel(cafeFirebase: CafeFirebase): CafeWithDistricts
    fun toCafeAddress(cafeEntity: CafeEntity): CafeAddress
}