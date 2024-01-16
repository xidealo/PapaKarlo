package com.bunbeauty.shared.data.mapper.street

import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.db.StreetEntity
import com.bunbeauty.shared.domain.model.street.Street

@Deprecated("Unused")
interface IStreetMapper {

    fun toStreetEntity(street: StreetServer): StreetEntity
    fun toStreet(street: StreetEntity): Street
    fun toStreet(street: StreetServer): Street

}