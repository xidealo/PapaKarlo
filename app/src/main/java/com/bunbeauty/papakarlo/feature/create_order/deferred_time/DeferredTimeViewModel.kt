package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.shared.domain.model.date_time.Time
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeferredTimeViewModel(
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableDeferredTimeSettings: MutableStateFlow<DeferredTimeSettings?> =
        MutableStateFlow(null)
    val deferredTimeSettings: StateFlow<DeferredTimeSettings?> =
        mutableDeferredTimeSettings.asStateFlow()

    init {
        val title: String = savedStateHandle["title"] ?: ""
        val selectedTime: Time? = savedStateHandle["selectedTime"]
        getDeferredTimeSettings(title, selectedTime)
    }

    private fun getDeferredTimeSettings(title: String, selectedTime: Time?) {
        viewModelScope.launch {
            val minTime = deferredTimeInteractor.getMinTime()
            mutableDeferredTimeSettings.value = DeferredTimeSettings(
                title = title,
                minTime = minTime,
                selectedTime = selectedTime ?: minTime
            )
        }
    }
}