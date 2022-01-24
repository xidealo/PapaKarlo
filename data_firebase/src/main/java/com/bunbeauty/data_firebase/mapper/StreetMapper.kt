package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.Street
import com.example.domain_firebase.mapper.IStreetMapper
import com.example.domain_firebase.model.entity.address.StreetEntity
import com.example.domain_firebase.model.firebase.address.StreetFirebase
import javax.inject.Inject

class StreetMapper @Inject constructor() : IStreetMapper {

    override fun toUIModel(streetEntity: StreetEntity): Street {
        return Street(
            uuid = streetEntity.uuid,
            name = streetEntity.name,
            cityUuid = streetEntity.districtUuid,
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