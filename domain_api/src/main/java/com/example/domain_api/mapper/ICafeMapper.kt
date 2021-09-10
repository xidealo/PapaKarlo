package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.product.MenuProduct
import com.example.domain_api.model.entity.CafeEntity
import com.example.domain_api.model.entity.MenuProductEntity
import com.example.domain_api.model.server.CafeServer
import com.example.domain_api.model.server.MenuProductServer

interface ICafeMapper {

    fun toEntityModel(cafe: CafeServer): CafeEntity
    fun toModel(cafe: CafeEntity): Cafe
}