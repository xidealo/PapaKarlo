package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.CafeAddressFirebase
import com.bunbeauty.domain.model.ui.Street
import com.bunbeauty.domain.model.ui.address.Address
import javax.inject.Inject

class AddressMapper @Inject constructor() : Mapper<CafeAddressFirebase, Address> {

    override fun from(model: CafeAddressFirebase): Address {
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
    override fun to(model: Address): CafeAddressFirebase {
        return CafeAddressFirebase(
            model.street,
            checkEmptyString(model.house),
            checkEmptyString(model.flat),
            checkEmptyString(model.entrance),
            checkEmptyString(model.comment),
            checkEmptyString(model.floor)
        )
    }
}