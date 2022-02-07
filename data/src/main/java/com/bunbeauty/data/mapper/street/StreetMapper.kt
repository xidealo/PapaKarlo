package com.bunbeauty.data.mapper.street

import com.bunbeauty.data.database.entity.StreetEntity
import com.bunbeauty.data.network.model.StreetServer
import com.bunbeauty.domain.model.Street

class StreetMapper : IStreetMapper {

    override fun toEntityModel(street: StreetServer): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
        )
    }

    override fun toEntityModel(street: Street): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
        )
    }

    override fun toModel(street: StreetEntity): Street {
        return Street(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
        )
    }

    override fun toModel(street: StreetServer): Street {
        return Street(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
        )
    }

    override fun toServerModel(street: Street): StreetServer {
        return StreetServer(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
            cafeUuid = "",
            isVisible = true
        )
    }

    override fun toServerModel(street: StreetEntity): StreetServer {
        return StreetServer(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
            cafeUuid = "",
            isVisible = true
        )
    }
}