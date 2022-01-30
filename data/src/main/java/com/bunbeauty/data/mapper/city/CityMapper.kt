package com.bunbeauty.data.mapper.city

import com.bunbeauty.data.database.entity.CityEntity
import com.bunbeauty.data.network.model.CityServer
import com.bunbeauty.domain.model.City
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