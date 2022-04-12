package com.bunbeauty.data.mapper.cafe

import com.bunbeauty.data.network.model.CafeServer
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import database.CafeEntity

interface ICafeMapper {

    fun toCafeEntity(cafeServer: CafeServer): CafeEntity
    fun toCafe(cafeEntity: CafeEntity): Cafe
    fun toCafe(cafeServer: CafeServer): Cafe
    fun toCafeAddress(cafeEntity: CafeEntity): CafeAddress
}