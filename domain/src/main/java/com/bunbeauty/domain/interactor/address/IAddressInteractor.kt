package com.bunbeauty.domain.interactor.address

import com.bunbeauty.domain.model.address.UserAddress

interface IAddressInteractor {

    suspend fun createAddress(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String
    ): UserAddress?
}