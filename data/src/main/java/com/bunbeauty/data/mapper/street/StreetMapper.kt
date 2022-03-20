package com.bunbeauty.data.mapper.street

import com.bunbeauty.data.network.model.StreetServer
import com.bunbeauty.domain.model.Street
import database.StreetEntity
import database.UserAddressEntity

class StreetMapper : IStreetMapper {

    override fun toStreetEntity(street: StreetServer): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
        )
    }

    override fun toStreetEntity(street: Street): StreetEntity {
        return StreetEntity(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid
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

    override fun toStreet(userAddressEntity: UserAddressEntity): Street {
        return Street(
            uuid = userAddressEntity.streetUuid,
            name = userAddressEntity.streetName,
            cityUuid = userAddressEntity.cityUuid
        )
    }

    override fun toStreetServer(street: Street): StreetServer {
        return StreetServer(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
            cafeUuid = "",
            isVisible = true
        )
    }

    override fun toStreetServer(street: StreetEntity): StreetServer {
        return StreetServer(
            uuid = street.uuid,
            name = street.name,
            cityUuid = street.cityUuid,
            cafeUuid = "",
            isVisible = true
        )
    }

    override fun toStreetServer(userAddressEntity: UserAddressEntity): StreetServer {
        return StreetServer(
            uuid = userAddressEntity.streetUuid,
            name = userAddressEntity.streetName,
            cityUuid = userAddressEntity.cityUuid,
            cafeUuid = "",
            isVisible = true
        )
    }
}