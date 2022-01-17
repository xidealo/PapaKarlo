package com.bunbeauty.papakarlo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.presentation.model.FieldError
import com.bunbeauty.presentation.model.Message
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var router: Router

    private val mutableMessage: MutableSharedFlow<Message> = MutableSharedFlow(replay = 0)
    val message: SharedFlow<Message> = mutableMessage.asSharedFlow()

    private val mutableError: MutableSharedFlow<Message> = MutableSharedFlow(replay = 0)
    val error: SharedFlow<Message> = mutableError.asSharedFlow()

    private val mutableFieldError = MutableSharedFlow<FieldError>(0)
    val fieldError: SharedFlow<FieldError> = mutableFieldError.asSharedFlow()

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

    protected inline fun <reified T> getNavArg(savedStateHandlekey: String): T? {
        return null ///savedStateHandle.get(key)
//        return when {
//            Parcelable::class.java.isAssignableFrom(T::class.java) -> {
//                 bundle?.getParcelable(key) as? T
//            }
//            T::class == Boolean::class -> {
//                bundle?.getBoolean(key) as T
//            }
//            T::class == String::class -> {
//                bundle?.getString(key) as T
//            }
//            else -> null
//        }
    }

    protected fun <T> Flow<T>.launchOnEach(block: (T) -> Unit): Job {
        return onEach { t ->
            block(t)
        }.launchIn(viewModelScope)
    }
}