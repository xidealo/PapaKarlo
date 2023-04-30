package com.bunbeauty.papakarlo.common.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Deprecated("Use SharedViewModel")
abstract class BaseViewModel : ViewModel(), KoinComponent {

    @Deprecated("Use event navigation on Fragments, example MenuFragment")
    val router: Router by inject()

    val resourcesProvider: IResourcesProvider by inject()

    protected fun <T> Flow<T>.launchOnEach(block: suspend (T) -> Unit): Job {
        return onEach { t ->
            block(t)
        }.launchIn(viewModelScope)
    }

    protected fun <T : Any?> T?.toState(errorMessage: String? = null): State<T> {
        return if (this == null) {
            State.Error(errorMessage ?: resourcesProvider.getString(R.string.common_error))
        } else {
            if (this is List<*> && isEmpty()) {
                State.Empty()
            } else {
                State.Success(this)
            }
        }
    }
}
