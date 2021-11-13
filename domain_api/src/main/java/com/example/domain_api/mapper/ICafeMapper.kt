package com.example.domain_api.mapper

import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.example.domain_api.model.entity.cafe.CafeEntity
import com.example.domain_api.model.server.CafeServer

interface ICafeMapper {

    fun toEntityModel(cafe: CafeServer): CafeEntity
    fun toModel(cafe: CafeEntity): Cafe
    fun toCafeAddress(cafe: CafeEntity): CafeAddress
}