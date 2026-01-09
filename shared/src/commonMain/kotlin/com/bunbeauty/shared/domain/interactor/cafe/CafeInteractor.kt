package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.core.Constants.SECONDS_IN_HOUR
import com.bunbeauty.core.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.core.Constants.TIME_DIVIDER
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.core.model.cafe.Cafe
import com.bunbeauty.core.domain.repo.CafeRepo

class CafeInteractor(
    private val cafeRepo: CafeRepo,
    private val dataStoreRepo: DataStoreRepo,
) : ICafeInteractor {
    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? = cafeRepo.getCafeByUuid(cafeUuid)

    override suspend fun saveSelectedCafe(cafeUuid: String) {
        val userUuid = dataStoreRepo.getUserUuid()
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()

        if (userUuid != null && selectedCityUuid != null) {
            cafeRepo.saveSelectedCafeUuid(userUuid, selectedCityUuid, cafeUuid)
        }
    }

    override fun getCafeTime(daySeconds: Int): String {
        val hours = daySeconds / SECONDS_IN_HOUR
        val minutes = (daySeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE
        val minutesString =
            if (minutes < 10) {
                "0$minutes"
            } else {
                minutes.toString()
            }

        return "$hours$TIME_DIVIDER$minutesString"
    }
}
