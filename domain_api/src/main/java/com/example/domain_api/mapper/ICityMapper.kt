package com.example.domain_api.mapper

import com.bunbeauty.domain.model.City
import com.example.domain_api.model.entity.CityEntity
import com.example.domain_api.model.server.CityServer

interface ICityMapper {

    fun toEntityModel(city: CityServer): CityEntity
    fun toModel(city: CityEntity): City
}