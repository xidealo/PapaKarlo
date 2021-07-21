package com.bunbeauty.papakarlo.presentation.base

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.presentation.view_model.base.adapter.FieldError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var router: Router

    var bundle: Bundle? = null

    private val mutableMessage: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val message: SharedFlow<String> = mutableMessage.asSharedFlow()

    private val mutableError: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)
    val error: SharedFlow<String> = mutableError.asSharedFlow()

    private val mutableFieldError = MutableSharedFlow<FieldError>(0)
    val fieldError: SharedFlow<FieldError> = mutableFieldError.asSharedFlow()

    protected fun showMessage(message: String) {
        viewModelScope.launch {
            mutableMessage.emit(message)
        }
    }

    protected fun showError(error: String) {
        viewModelScope.launch {
            mutableError.emit(error)
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

    protected inline fun <reified T> getNavArg(key: String): T? {
        return when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> {
                bundle?.getParcelable(key) as? T
            }
            T::class.java == Boolean::class.java -> {
                bundle?.getBoolean(key) as T
            }
            T::class.java == String::class.java -> {
                bundle?.getString(key) as T
            }
            else -> null
        }
    }
}