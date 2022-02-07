package com.bunbeauty.data.mapper.cafe

import com.bunbeauty.data.database.entity.cafe.CafeEntity
import com.bunbeauty.data.network.model.CafeServer
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe

class CafeMapper  constructor() : ICafeMapper {

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