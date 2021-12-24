package com.bunbeauty.domain.interactor.cafe

import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafePreview
import kotlinx.coroutines.flow.Flow

interface ICafeInteractor {

    fun observeCafeList(): Flow<List<CafePreview>>
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?
    suspend fun selectCafe(cafeUuid: String)
}