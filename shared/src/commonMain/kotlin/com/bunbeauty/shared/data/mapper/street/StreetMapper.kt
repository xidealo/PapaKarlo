package com.bunbeauty.shared.data.mapper.street

import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.db.StreetEntity
import com.bunbeauty.shared.domain.model.street.Street

@Deprecated("Unused")
class StreetMapper : IStreetMapper {

    override fun toStreetEntity(street: StreetServer): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
        )
    }

    override fun toStreet(street: StreetEntity): Street {
        return Street(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
        )
    }

    override fun toStreet(street: StreetServer): Street {
        return Street(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
        )
    }
}