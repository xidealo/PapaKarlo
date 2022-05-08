package com.bunbeauty.shared.data.mapper.cafe

import com.bunbeauty.shared.data.network.model.CafeServer
import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import database.CafeEntity

interface ICafeMapper {

    fun toCafeEntity(cafeServer: CafeServer): CafeEntity
    fun toCafe(cafeEntity: CafeEntity): Cafe
    fun toCafe(cafeServer: CafeServer): Cafe
    fun toCafeAddress(cafeEntity: CafeEntity): CafeAddress
}