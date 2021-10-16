package com.bunbeauty.papakarlo.presentation.create_order

import com.bunbeauty.common.Constants.MIN_DEFERRED_HOURS_ADDITION
import com.bunbeauty.common.Constants.MIN_DEFERRED_MINUTES_ADDITION
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import javax.inject.Inject

class DeferredTimeViewModel @Inject constructor(
    private val dateTimeUtils: IDateTimeUtil
) : BaseViewModel() {

    val minTimeHour: Int
        get() = dateTimeUtils.getTimeHourIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        )
    val minTimeMinute: Int
        get() = dateTimeUtils.getTimeMinuteIn(
            MIN_DEFERRED_HOURS_ADDITION,
            MIN_DEFERRED_MINUTES_ADDITION
        )

    fun getSelectedMillis(hour: Int, minute: Int): Long {
        return dateTimeUtils.getMillis(hour, minute)
    }
}