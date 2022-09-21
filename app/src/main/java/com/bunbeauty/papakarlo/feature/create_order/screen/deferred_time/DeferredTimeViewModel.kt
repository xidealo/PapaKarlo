package com.bunbeauty.papakarlo.feature.create_order.screen.deferred_time

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.create_order.mapper.TimeMapper
import com.bunbeauty.papakarlo.feature.create_order.model.DeferredTimeSettingsUI
import com.bunbeauty.papakarlo.feature.create_order.model.TimeUI
import com.bunbeauty.shared.domain.interactor.deferred_time.IDeferredTimeInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeferredTimeViewModel(
    private val deferredTimeInteractor: IDeferredTimeInteractor,
    private val timeMapper: TimeMapper,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val mutableDeferredTimeSettingsUI: MutableStateFlow<DeferredTimeSettingsUI?> =
        MutableStateFlow(null)
    val deferredTimeSettingsUI: StateFlow<DeferredTimeSettingsUI?> =
        mutableDeferredTimeSettingsUI.asStateFlow()

    init {
        val title: String = savedStateHandle["title"] ?: ""
        val selectedTime: TimeUI? = savedStateHandle["selectedTime"]
        getDeferredTimeSettings(title, selectedTime)
    }

    private fun getDeferredTimeSettings(title: String, selectedTime: TimeUI?) {
        viewModelScope.launch {
            val minTime = timeMapper.toUiModel(deferredTimeInteractor.getMinTime())
            mutableDeferredTimeSettingsUI.value = DeferredTimeSettingsUI(
                title = title,
                minTime = minTime,
                selectedTime = selectedTime ?: minTime
            )
        }
    }
}