package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.model.ui.address.CafeAddress
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun refreshCafeList()
    suspend fun getCafeEntityByUuid(cafeUuid: String): CafeEntity
    suspend fun getCafeByUuid(cafeUuid: String): Cafe
    suspend fun getCafeByStreetUuid(streetUuid: String): Cafe
    fun observeCafeList(): Flow<List<Cafe>>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress>
}
