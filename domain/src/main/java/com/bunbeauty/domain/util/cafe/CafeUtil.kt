package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.R
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import javax.inject.Inject

class CafeUtil @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil
) : ICafeUtil {

    override fun getIsClosedColorId(cafe: Cafe): Int {
        val beforeStart = dateTimeUtil.getMinutesFromNowToTime(cafe.fromTime)
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(cafe.toTime)

        return if (beforeStart < 0 && beforeEnd > 0) {
            R.color.light_green
        } else {
            R.color.light_red
        }
    }
}