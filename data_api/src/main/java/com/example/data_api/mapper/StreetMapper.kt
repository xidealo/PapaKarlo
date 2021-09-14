package com.example.data_api.mapper

import com.bunbeauty.domain.model.Street
import com.example.domain_api.mapper.IStreetMapper
import com.example.domain_api.model.entity.StreetEntity
import com.example.domain_api.model.server.StreetServer
import javax.inject.Inject

class StreetMapper @Inject constructor() : IStreetMapper {

    override fun toEntityModel(street: StreetServer): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
        )
    }

    override fun toModel(street: StreetEntity): Street {
        return Street(
            uuid = street.uuid,
            name = street.name
        )
    }
}