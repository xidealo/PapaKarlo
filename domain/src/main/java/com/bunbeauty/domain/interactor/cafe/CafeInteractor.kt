package com.bunbeauty.domain.interactor.cafe

import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CafeInteractor @Inject constructor(
    @Api private val cafeRepo: CafeRepo,
    private val dateTimeUtil: IDateTimeUtil
) : ICafeInteractor {

    override fun observeCafeList(): Flow<List<CafePreview>> {
        return cafeRepo.observeCafeList().map { observedCafeList ->
            observedCafeList.map { cafe ->
                CafePreview(
                    uuid = cafe.uuid,
                    fromTime = cafe.fromTime,
                    toTime = cafe.toTime,
                    address = cafe.address,
                    isOpen = cafe.isOpen(),
                    willCloseIn = cafe.getCloseIn(),
                )
            }
        }
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeRepo.getCafeByUuid(cafeUuid)
    }

    fun Cafe.isOpen(): Boolean {
        val beforeStart = dateTimeUtil.getMinutesFromNowToTime(fromTime)
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(toTime)

        return beforeStart < 0 && beforeEnd > 0
    }

    fun Cafe.getCloseIn(): Int? {
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(toTime)

        return if (beforeEnd in 1 until 60) {
            beforeEnd % 60
        } else {
            null
        }
    }
}