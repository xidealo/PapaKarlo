package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import javax.inject.Inject

class CafeUtil @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil
) : ICafeUtil {

    override fun getIsOpen(cafe: Cafe): Boolean {
        val beforeStart = dateTimeUtil.getMinutesFromNowToTime(cafe.fromTime)
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(cafe.toTime)

        return beforeStart < 0 && beforeEnd > 0
    }
}