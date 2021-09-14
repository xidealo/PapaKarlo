package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun refreshCafeList()
    fun observeCafeList(): Flow<List<Cafe>>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?>
}
