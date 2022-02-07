package com.bunbeauty.data.mapper.city

import com.bunbeauty.data.database.entity.CityEntity
import com.bunbeauty.data.network.model.CityServer
import com.bunbeauty.domain.model.City

class CityMapper  constructor() : ICityMapper {

    override fun toCityEntity(cityServer: CityServer): CityEntity {
        return CityEntity(
            uuid = cityServer.uuid,
            name = cityServer.name,
            timeZone = cityServer.timeZone,
            isVisible = cityServer.isVisible
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