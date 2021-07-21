package com.bunbeauty.papakarlo.presentation.create_order

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.util.date_time.IDateTimeUtil
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeferredTimeViewModel @Inject constructor(
    private val dateTimeUtils: IDateTimeUtil,
    private val stringUtil: IStringUtil,
) : BaseViewModel() {

    private val mutableSelectedTime: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val selectedTime: SharedFlow<String> = mutableSelectedTime.asSharedFlow()

    val currentTimeHour: Int
        get() = dateTimeUtils.currentTimeHour

    val currentTimeMinute: Int
        get() = dateTimeUtils.currentTimeMinute

    fun onTimeSelected(hour: Int, minute: Int) {
        val selectedTime = stringUtil.getTimeString(hour, minute)
        val currentTime = stringUtil.getTimeString(currentTimeHour, currentTimeMinute)

        if (selectedTime > currentTime) {
            viewModelScope.launch {
                mutableSelectedTime.emit(selectedTime)
            }
        }
    }
}