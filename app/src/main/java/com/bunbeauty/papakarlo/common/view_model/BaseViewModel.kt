package com.bunbeauty.papakarlo.common.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.common.model.FieldError
import com.bunbeauty.papakarlo.common.model.Message
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    //TODO(Move to constructor)
    val router: Router by inject()

    private val mutableMessage: MutableSharedFlow<Message> = MutableSharedFlow(replay = 0)
    val message: SharedFlow<Message> = mutableMessage.asSharedFlow()

    private val mutableError: MutableSharedFlow<Message> = MutableSharedFlow(replay = 0)
    val error: SharedFlow<Message> = mutableError.asSharedFlow()

    private val mutableFieldError = MutableSharedFlow<FieldError>(0)
    val fieldError: SharedFlow<FieldError> = mutableFieldError.asSharedFlow()

    protected fun <T> MutableSharedFlow<T>.launchEmit(value: T) {
        viewModelScope.launch {
            emit(value)
        }
    }

    fun showMessage(message: String, isTop: Boolean) {
        viewModelScope.launch {
            mutableMessage.emit(
                Message(
                    message = message,
                    isTop = isTop
                )
            )
        }
    }

    fun showError(error: String, isTop: Boolean) {
        viewModelScope.launch {
            mutableError.emit(
                Message(
                    message = error,
                    isTop = isTop
                )
            )
        }
    }

    protected fun sendFieldError(fieldKey: String, error: String) {
        viewModelScope.launch {
            mutableFieldError.emit(FieldError(fieldKey, error))
        }
    }

    fun goBack() {
        router.navigateUp()
    }

    protected fun <T> Flow<T>.launchOnEach(block: (T) -> Unit): Job {
        return onEach { t ->
            block(t)
        }.launchIn(viewModelScope)
    }
}