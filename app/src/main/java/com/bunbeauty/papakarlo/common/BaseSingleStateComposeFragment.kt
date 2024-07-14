package com.bunbeauty.papakarlo.common

import androidx.compose.runtime.Composable
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewDataState

abstract class BaseSingleStateComposeFragment<VDS : BaseViewDataState, A : BaseAction, E : BaseEvent> :
    BaseComposeFragment<VDS, VDS, A, E>() {

    @Composable
    override fun VDS.mapState(): VDS {
        return this
    }
}
