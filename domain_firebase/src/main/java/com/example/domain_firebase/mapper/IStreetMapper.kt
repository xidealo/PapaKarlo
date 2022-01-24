package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.Street
import com.example.domain_firebase.model.entity.address.StreetEntity
import com.example.domain_firebase.model.firebase.address.StreetFirebase

interface IStreetMapper {
    fun toUIModel(streetEntity: StreetEntity): Street
    fun toEntityModel(streetFirebase: StreetFirebase, districtUuid: String): StreetEntity
}