package com.bunbeauty.shared.data.mapper.cafe

import com.bunbeauty.shared.data.network.model.CafeServer
import com.bunbeauty.shared.db.CafeEntity
import com.bunbeauty.shared.domain.model.cafe.Cafe

fun CafeServer.toCafeEntity(): CafeEntity {
    return CafeEntity(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        offset = 3,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid,
        isVisible = isVisible,
        workType = workType,
        workload = workload,
        additionalUtensils = additionalUtensils
    )
}

fun CafeEntity.toCafe(): Cafe {
    return Cafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid,
        isVisible = isVisible,
        workType = Cafe.WorkType.valueOf(workType),
        workload = Cafe.Workload.valueOf(workload),
        additionalUtensils = additionalUtensils
    )
}

fun CafeServer.toCafe(): Cafe {
    return Cafe(
        uuid = uuid,
        fromTime = fromTime,
        toTime = toTime,
        phone = phone,
        latitude = latitude,
        longitude = longitude,
        address = address,
        cityUuid = cityUuid,
        isVisible = isVisible,
        workType = Cafe.WorkType.valueOf(workType),
        workload = Cafe.Workload.valueOf(workload),
        additionalUtensils = additionalUtensils
    )
}
