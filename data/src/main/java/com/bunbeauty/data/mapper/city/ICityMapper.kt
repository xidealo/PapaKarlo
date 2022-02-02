package com.bunbeauty.data.mapper.city

import com.bunbeauty.data.database.entity.CityEntity
import com.bunbeauty.data.network.model.CityServer
import com.bunbeauty.domain.model.City

interface ICityMapper {

    fun toCityEntity(cityServer: CityServer): CityEntity
    fun toCity(cityEntity: CityEntity): City
}