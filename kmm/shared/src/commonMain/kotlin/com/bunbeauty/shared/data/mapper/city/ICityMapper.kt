package com.bunbeauty.shared.data.mapper.city

import com.bunbeauty.shared.data.network.model.CityServer
import com.bunbeauty.shared.domain.model.City
import database.CityEntity

interface ICityMapper {

    fun toCityEntity(cityServer: CityServer): CityEntity
    fun toCity(cityServer: CityServer): City
    fun toCity(cityEntity: CityEntity): City
}