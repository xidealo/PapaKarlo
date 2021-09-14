package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Street
import com.example.domain_api.model.entity.StreetEntity
import com.example.domain_api.model.server.StreetServer

interface IStreetMapper {

    fun toEntityModel(street: StreetServer): StreetEntity
    fun toModel(street: StreetEntity): Street
}