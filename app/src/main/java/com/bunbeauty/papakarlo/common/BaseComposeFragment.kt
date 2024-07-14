package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.base.SharedStateViewModel

abstract class BaseComposeFragment<DS : BaseDataState, VS : BaseViewState, A : BaseAction, E : BaseEvent> :
    BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    abstract val viewModel: SharedStateViewModel<DS, A, E>

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val dataState by viewModel.dataState.collectAsStateWithLifecycle()
            val onAction = remember {
                { action: A ->
                    viewModel.onAction(action)
                }
            }
            Screen(
                viewState = dataState.mapState(),
                onAction = onAction
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

    abstract fun handleEvent(event: E)

    @Composable
    abstract fun DS.mapState(): VS

    @Composable
    abstract fun Screen(viewState: VS, onAction: (A) -> Unit)
}
