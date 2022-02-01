package com.bunbeauty.data.mapper.street

import com.bunbeauty.data.database.entity.StreetEntity
import com.bunbeauty.data.network.model.StreetServer
import com.bunbeauty.domain.model.Street

interface IStreetMapper {

    fun toEntityModel(street: StreetServer): StreetEntity
    fun toEntityModel(street: Street): StreetEntity
    fun toModel(street: StreetEntity): Street
    fun toModel(street: StreetServer): Street
    fun toServerModel(street: Street): StreetServer
    fun toServerModel(street: StreetEntity): StreetServer
}