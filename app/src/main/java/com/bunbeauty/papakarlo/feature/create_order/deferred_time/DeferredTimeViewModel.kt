package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.domain.model.datee_time.Time
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeferredTimeViewModel @Inject constructor(
    private val resourcesProvider: IResourcesProvider,
    private val deferredTimeInteractor: IDeferredTimeInteractor,
) : BaseViewModel() {

    private val mutableDeferredTimeState: MutableStateFlow<DeferredTimeState> =
        MutableStateFlow(DeferredTimeState.Default)
    val deferredTimeState: StateFlow<DeferredTimeState> = mutableDeferredTimeState.asStateFlow()

    private var selectedTime: Time? = null

    fun setSelectedTime(selectedHour: Int, selectedMinute: Int) {
        selectedTime = if (selectedHour != -1 && selectedMinute != -1) {
            Time(selectedHour, selectedMinute)
        } else {
            null
        }
    }

    fun onSelectTimeClicked() {
        viewModelScope.launch {
            if (deferredTimeInteractor.isDeferredTimeAvailable()) {
                val minTime = deferredTimeInteractor.getMinTime()
                mutableDeferredTimeState.value = DeferredTimeState.SelectTime(
                    minTime = minTime,
                    selectedTime = selectedTime ?: minTime
                )
            } else {
                showError(
                    resourcesProvider.getString(R.string.error_deferred_time_not_available),
                    true
                )
            }
        }
    }

    fun onSelectTimeCanceled() {
        mutableDeferredTimeState.value = DeferredTimeState.Default
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        viewModelScope.launch {
            deferredTimeInteractor.getDeferredTimeMillis(hour, minute).let { selectedTimeMillis ->
                mutableDeferredTimeState.value = DeferredTimeState.TimeSelected(selectedTimeMillis)
            }
        }
    }

    fun onTimeSelectedAsap() {
        mutableDeferredTimeState.value = DeferredTimeState.TimeSelected(null)
    }
}