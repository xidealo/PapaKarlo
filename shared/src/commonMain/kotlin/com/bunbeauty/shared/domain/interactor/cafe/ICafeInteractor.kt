package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.core.model.cafe.Cafe

interface ICafeInteractor {
    fun getCafeTime(daySeconds: Int): String

    suspend fun getCafeByUuid(cafeUuid: String): Cafe?

    suspend fun saveSelectedCafe(cafeUuid: String)
}
