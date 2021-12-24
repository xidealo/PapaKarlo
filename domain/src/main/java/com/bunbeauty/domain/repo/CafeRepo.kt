package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun refreshCafeList(selectedCityUuid: String)
    suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String
    )

    suspend fun getCafeList(): List<Cafe>
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?

    suspend fun observeSelectedCafe(): Flow<Cafe?>
    fun observeCafeList(): Flow<List<Cafe>>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?>
}
