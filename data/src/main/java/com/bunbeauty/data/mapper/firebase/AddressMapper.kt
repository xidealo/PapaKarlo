package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.AddressFirebase
import com.bunbeauty.domain.model.local.Street
import com.bunbeauty.domain.model.local.address.Address
import javax.inject.Inject

class AddressMapper @Inject constructor() : Mapper<AddressFirebase, Address> {

    override fun from(model: AddressFirebase): Address {
        return Address().apply {
            uuid = "empty uuid"
            street = model.street ?: Street()
            house = model.house ?: ""
            flat = model.flat ?: ""
            entrance = model.entrance ?: ""
            comment = model.comment ?: ""
            floor = model.floor ?: ""
        }
    }

    /**
     * Set uuid, id after convert
     */
    override fun to(model: Address): AddressFirebase {
        return AddressFirebase(
            model.street,
            checkEmptyString(model.house),
            checkEmptyString(model.flat),
            checkEmptyString(model.entrance),
            checkEmptyString(model.comment),
            checkEmptyString(model.floor)
        )
    }
}