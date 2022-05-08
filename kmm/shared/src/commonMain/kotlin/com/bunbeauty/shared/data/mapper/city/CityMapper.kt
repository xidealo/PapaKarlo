package com.bunbeauty.shared.data.mapper.city

import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.domain.model.City
import database.CityEntity

class CityMapper : ICityMapper {

    override fun toCityEntity(cityServer: CityServer): CityEntity {
        return CityEntity(
            uuid = cityServer.uuid,
            name = cityServer.name,
            timeZone = cityServer.timeZone,
            isVisible = cityServer.isVisible
        )
    }

    override fun toCity(cityServer: CityServer): City {
        return City(
            uuid = cityServer.uuid,
            name = cityServer.name,
            timeZone = cityServer.timeZone,
        )
    }

    override fun toCity(cityEntity: CityEntity): City {
        return City(
            uuid = cityEntity.uuid,
            name = cityEntity.name,
            timeZone = cityEntity.timeZone,
        )
    }
}