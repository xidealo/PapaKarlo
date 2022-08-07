package com.bunbeauty.shared.domain.interactor.address

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.address.UserAddress
import kotlinx.coroutines.flow.Flow

interface IAddressInteractor {

    suspend fun createAddress(
        streetName: String,
        house: String,
        flat: String,
        entrance: String,
        comment: String,
        floor: String
    ): UserAddress?

    fun observeAddressList(): CommonFlow<List<UserAddress>>

    fun observeAddress(): CommonFlow<UserAddress?>

    suspend fun saveSelectedUserAddress(addressUuid: String)
}