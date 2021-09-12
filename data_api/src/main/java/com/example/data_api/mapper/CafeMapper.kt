package com.example.data_api.mapper

import com.bunbeauty.domain.model.Cafe
import com.example.domain_api.mapper.ICafeMapper
import com.example.domain_api.model.entity.CafeEntity
import com.example.domain_api.model.server.CafeServer
import javax.inject.Inject

class CafeMapper @Inject constructor() : ICafeMapper {

    override fun toEntityModel(cafe: CafeServer): CafeEntity {
        return CafeEntity(
            uuid = cafe.uuid,
            address = cafe.address,
            fromTime = cafe.fromTime,
            toTime = cafe.toTime,
            phone = cafe.phone,
            latitude = cafe.latitude,
            longitude = cafe.longitude,
            visible = cafe.visible,
            city = cafe.city,
        )
    }

    override fun toModel(cafe: CafeEntity): Cafe {
        return Cafe(
            uuid = cafe.uuid,
            address = cafe.address,
            fromTime = cafe.fromTime,
            toTime = cafe.toTime,
            phone = cafe.phone,
            latitude = cafe.latitude,
            longitude = cafe.longitude,
            visible = cafe.visible,
            city = cafe.city,
        )
    }
}