package com.bunbeauty.papakarlo.presentation.create_order

import com.bunbeauty.domain.interactor.deferred_time.IDeferredTimeInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import javax.inject.Inject

class DeferredTimeViewModel @Inject constructor(
    private val deferredTimeInteractor: IDeferredTimeInteractor,
) : BaseViewModel() {

    val minTimeHour: Int
        get() = deferredTimeInteractor.getMinTimeHours()

    val minTimeMinute: Int
        get() = deferredTimeInteractor.getMinTimeMinutes()

    fun getSelectedMillis(hour: Int, minute: Int): Long {
        return deferredTimeInteractor.getDeferredTimeMillis(hour, minute)
    }
}