package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun refreshCafeList(selectedCityUuid: String)
    suspend fun saveSelectedCafeUuid(cafeUuid: String)

    suspend fun getCafeList(): List<Cafe>
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?

    suspend fun observeSelectedCafe(): Flow<Cafe?>
    fun observeCafeList(): Flow<List<Cafe>>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?>
}
