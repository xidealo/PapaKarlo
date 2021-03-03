package com.bunbeauty.domain.util.cafe

import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import javax.inject.Inject

class CafeUtil @Inject constructor(
    private val dateTimeUtil: IDateTimeUtil
) : ICafeUtil