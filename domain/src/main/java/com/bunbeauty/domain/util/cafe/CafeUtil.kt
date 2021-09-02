package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.R
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import javax.inject.Inject

class CafeUtil @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil,
    private val resourcesProvider: IResourcesProvider,
) : ICafeUtil {

    override fun getIsClosedColor(cafe: Cafe): Int {
        val beforeStart = dateTimeUtil.getMinutesFromNowToTime(cafe.fromTime)
        val beforeEnd = dateTimeUtil.getMinutesFromNowToTime(cafe.toTime)

        return if (beforeStart < 0 && beforeEnd > 0) {
            resourcesProvider.getColor(R.color.light_green)
        } else {
            resourcesProvider.getColor(R.color.light_red)
        }
    }
}