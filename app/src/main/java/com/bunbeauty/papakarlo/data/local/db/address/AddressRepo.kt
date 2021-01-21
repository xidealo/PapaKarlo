package com.bunbeauty.papakarlo.data.local.db.address

import com.bunbeauty.papakarlo.data.model.Address

interface AddressRepo {
   suspend fun insert(address: Address)
}