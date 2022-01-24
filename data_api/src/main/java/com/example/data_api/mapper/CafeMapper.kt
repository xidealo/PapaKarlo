package com.example.data_api.mapper

import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.example.domain_api.mapper.ICafeMapper
import com.example.domain_api.model.entity.cafe.CafeEntity
import com.example.domain_api.model.server.CafeServer
import javax.inject.Inject

class CafeMapper @Inject constructor() : ICafeMapper {

    override fun toEntityModel(cafe: CafeServer): CafeEntity {
        return CafeEntity(
            uuid = cafe.uuid,
            fromTime = cafe.fromTime,
            toTime = cafe.toTime,
            offset = cafe.toTime,
            phone = cafe.phone,
            latitude = cafe.latitude,
            longitude = cafe.longitude,
            address = cafe.address,
            cityUuid = cafe.cityUuid,
            isVisible = cafe.isVisible,
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
            cityUuid = cafe.cityUuid,
        )
    }

    override fun toCafeAddress(cafe: CafeEntity): CafeAddress {
        return CafeAddress(
            address = cafe.address,
            cafeUuid = cafe.uuid,
        )
    }
}