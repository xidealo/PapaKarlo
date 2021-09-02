package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.address.StreetEntity
import com.bunbeauty.domain.model.firebase.address.StreetFirebase
import com.bunbeauty.domain.model.ui.Street

interface IStreetMapper {
    fun toUIModel(streetEntity: StreetEntity): Street
    fun toEntityModel(streetFirebase: StreetFirebase, districtUuid: String): StreetEntity
}