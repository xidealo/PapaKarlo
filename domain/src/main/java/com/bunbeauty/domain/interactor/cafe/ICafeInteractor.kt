package com.bunbeauty.domain.interactor.cafe

import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafeAddress
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.domain.model.cafe.CafeStatus
import kotlinx.coroutines.flow.Flow

interface ICafeInteractor {

    fun observeCafeList(): Flow<List<Cafe>?>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeSelectedCafeAddress(): Flow<CafeAddress>
    suspend fun getCafeList(): List<Cafe>?
    suspend fun getCafeStatus(cafe: Cafe): CafeStatus
    suspend fun isClosed(cafe: Cafe): Boolean
    suspend fun getCloseIn(cafe: Cafe): Int?
    fun getCafeTime(daySeconds: Int): String
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?
    suspend fun selectCafe(cafeUuid: String)
}