package com.bunbeauty.shared.data.mapper.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.db.CityEntity

class CityMapper : ICityMapper {
    override fun toCityEntity(cityServer: CityServer): CityEntity =
        CityEntity(
            uuid = cityServer.uuid,
            name = cityServer.name,
            timeZone = cityServer.timeZone,
            isVisible = cityServer.isVisible,
        )

    override fun toCity(cityServer: CityServer): City =
        City(
            uuid = cityServer.uuid,
            name = cityServer.name,
            timeZone = cityServer.timeZone,
        )

    override fun toCity(cityEntity: CityEntity): City =
        City(
            uuid = cityEntity.uuid,
            name = cityEntity.name,
            timeZone = cityEntity.timeZone,
        )
}
