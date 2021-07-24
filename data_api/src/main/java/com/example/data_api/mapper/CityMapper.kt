package com.example.data_api.mapper

import com.bunbeauty.domain.model.City
import com.example.domain_api.mapper.ICityMapper
import com.example.domain_api.model.entity.CityEntity
import com.example.domain_api.model.server.CityServer
import javax.inject.Inject

class CityMapper @Inject constructor() : ICityMapper {

    override fun toEntityModel(city: CityServer): CityEntity {
        return CityEntity(
            uuid = city.uuid,
            name = city.name,
        )
    }

    override fun toModel(city: CityEntity): City {
        return City(
            uuid = city.uuid,
            name = city.name,
        )
    }
}