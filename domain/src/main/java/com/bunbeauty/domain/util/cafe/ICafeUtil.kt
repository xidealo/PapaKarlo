package com.bunbeauty.domain.util.cafe

import org.joda.time.DateTime

interface ICafeUtil {
    fun getIsClosedMessage(fromTime: String, toTime: String, now: DateTime): String
    fun getIsClosedColor(fromTime: String, toTime: String, now: DateTime): Int
}