package com.bunbeauty.data.mapper.cafe

import com.bunbeauty.data.network.model.CafeServer
import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import database.CafeEntity

class CafeMapper : ICafeMapper {

    override fun toCafeEntity(cafeServer: CafeServer): CafeEntity {
        return CafeEntity(
            uuid = cafeServer.uuid,
            fromTime = cafeServer.fromTime,
            toTime = cafeServer.toTime,
            offset = cafeServer.toTime,
            phone = cafeServer.phone,
            latitude = cafeServer.latitude,
            longitude = cafeServer.longitude,
            address = cafeServer.address,
            cityUuid = cafeServer.cityUuid,
            isVisible = cafeServer.isVisible,
        )
    }

    override fun toCafe(cafeEntity: CafeEntity): Cafe {
        return Cafe(
            uuid = cafeEntity.uuid,
            address = cafeEntity.address,
            fromTime = cafeEntity.fromTime,
            toTime = cafeEntity.toTime,
            phone = cafeEntity.phone,
            latitude = cafeEntity.latitude,
            longitude = cafeEntity.longitude,
            cityUuid = cafeEntity.cityUuid,
        )
    }

    override fun toCafe(cafeServer: CafeServer): Cafe {
        return Cafe(
            uuid = cafeServer.uuid,
            address = cafeServer.address,
            fromTime = cafeServer.fromTime,
            toTime = cafeServer.toTime,
            phone = cafeServer.phone,
            latitude = cafeServer.latitude,
            longitude = cafeServer.longitude,
            cityUuid = cafeServer.cityUuid,
        )
    }

    override fun toCafeAddress(cafeEntity: CafeEntity): CafeAddress {
        return CafeAddress(
            address = cafeEntity.address,
            cafeUuid = cafeEntity.uuid,
        )
    }
}