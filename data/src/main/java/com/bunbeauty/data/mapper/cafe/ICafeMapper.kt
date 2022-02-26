package com.bunbeauty.data.mapper.cafe

import com.bunbeauty.data.network.model.CafeServer
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import database.CafeEntity

interface ICafeMapper {

    fun toEntityModel(cafe: CafeServer): CafeEntity
    fun toModel(cafe: CafeEntity): Cafe
    fun toCafeAddress(cafe: CafeEntity): CafeAddress
}