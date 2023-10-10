package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseState
import com.bunbeauty.shared.presentation.base.SharedStateViewModel

abstract class BaseComposeFragment<State : BaseState, Action : BaseAction, Event : BaseEvent> :
    BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    abstract val viewModel: SharedStateViewModel<State, Action, Event>

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val state by viewModel.state.collectAsStateWithLifecycle()
            Screen(
                state = state,
                onAction = viewModel::handleAction
            )

            val events by viewModel.events.collectAsStateWithLifecycle()
            LaunchedEffect(events) {
                events.forEach { event ->
                    handleEvent(event)
                }
                viewModel.consumeEvents(events)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    @Composable
    abstract fun Screen(state: State, onAction: (Action) -> Unit)
}
