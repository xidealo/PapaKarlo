package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IStreetMapper
import com.bunbeauty.domain.model.entity.address.StreetEntity
import com.bunbeauty.domain.model.firebase.address.StreetFirebase
import com.bunbeauty.domain.model.ui.Street
import javax.inject.Inject

class StreetMapper @Inject constructor() : IStreetMapper {

    override fun toUIModel(streetEntity: StreetEntity): Street {
        return Street(
            uuid = streetEntity.uuid,
            name = streetEntity.name,
            districtUuid = streetEntity.districtUuid
        )
    }

    override fun toEntityModel(streetFirebase: StreetFirebase, districtUuid: String): StreetEntity {
        return StreetEntity(
            uuid = streetFirebase.id,
            name = streetFirebase.name,
            districtUuid = districtUuid
        )
    }
}