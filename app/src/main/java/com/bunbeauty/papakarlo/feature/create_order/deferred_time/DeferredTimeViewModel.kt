package com.bunbeauty.papakarlo.feature.create_order.deferred_time

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeferredTimeViewModel constructor(
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableDeferredTimeSettings: MutableStateFlow<DeferredTimeSettings?> =
        MutableStateFlow(null)
    val deferredTimeSettings: StateFlow<DeferredTimeSettings?> =
        mutableDeferredTimeSettings.asStateFlow()

    init {
        getDeferredTimeSettings()
    }

    private fun getDeferredTimeSettings() {
        viewModelScope.launch {
            val minTime = deferredTimeInteractor.getMinTime()
            mutableDeferredTimeSettings.value = DeferredTimeSettings(
                title = savedStateHandle["title"] ?: "",
                minTime = minTime,
                selectedTime = savedStateHandle["selectedTime"] ?: minTime
            )
        }
    }
}