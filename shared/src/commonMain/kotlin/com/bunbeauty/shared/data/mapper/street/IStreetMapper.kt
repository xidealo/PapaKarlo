package com.bunbeauty.shared.data.mapper.street

import com.bunbeauty.shared.data.network.model.StreetServer
import com.bunbeauty.shared.db.StreetEntity
import com.bunbeauty.shared.db.UserAddressEntity
import com.bunbeauty.shared.domain.model.street.Street

interface IStreetMapper {

    fun toStreetEntity(street: StreetServer): StreetEntity
    fun toStreetEntity(street: Street): StreetEntity
    fun toStreet(street: StreetEntity): Street
    fun toStreet(street: StreetServer): Street
    fun toStreet(userAddressEntity: UserAddressEntity): Street
    fun toStreetServer(street: Street): StreetServer
    fun toStreetServer(street: StreetEntity): StreetServer
    fun toStreetServer(userAddressEntity: UserAddressEntity): StreetServer
}