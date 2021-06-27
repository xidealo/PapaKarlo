package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.model.address.Address
import com.bunbeauty.domain.model.firebase.AddressFirebase
import javax.inject.Inject

class AddressMapper @Inject constructor() : Mapper<AddressFirebase, Address> {

    override fun from(e: Address): AddressFirebase {
        return AddressFirebase(
            e.street,
            checkEmptyString(e.house),
            checkEmptyString(e.flat),
            checkEmptyString(e.entrance),
            checkEmptyString(e.intercom),
            checkEmptyString(e.floor)
        )
    }

    /**
     * Set uuid, id after convert
     */
    override fun to(t: AddressFirebase): Address {
        return Address().apply {
            uuid = "empty uuid"
            street = t.street ?: Street()
            house = t.house ?: ""
            flat = t.flat ?: ""
            entrance = t.entrance ?: ""
            intercom = t.intercom ?: ""
            floor = t.floor ?: ""
        }
    }


}