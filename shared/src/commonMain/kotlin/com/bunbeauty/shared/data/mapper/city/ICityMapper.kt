package com.bunbeauty.shared.data.mapper.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.db.CityEntity

interface ICityMapper {
    fun toCityEntity(cityServer: CityServer): CityEntity

    fun toCity(cityServer: CityServer): City

    fun toCity(cityEntity: CityEntity): City
}
