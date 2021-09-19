package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Street
import com.example.domain_api.model.entity.StreetEntity
import com.example.domain_api.model.server.StreetServer

interface IStreetMapper {

    fun toEntityModel(street: StreetServer): StreetEntity
    fun toEntityModel(street: Street): StreetEntity
    fun toModel(street: StreetEntity): Street
    fun toServerModel(street: Street): StreetServer
    fun toServerModel(street: StreetEntity): StreetServer
}