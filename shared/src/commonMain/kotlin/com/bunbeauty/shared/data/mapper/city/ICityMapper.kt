package com.bunbeauty.shared.data.mapper.city

import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.db.CityEntity
import com.bunbeauty.shared.domain.model.city.City

interface ICityMapper {

    fun toCityEntity(cityServer: CityServer): CityEntity
    fun toCity(cityServer: CityServer): City
    fun toCity(cityEntity: CityEntity): City
}